package org.karigor.friends.view

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.karigor.friends.R
import org.karigor.friends.databinding.ActivityMainBinding
import org.karigor.friends.databinding.DialogFullInfoBinding
import org.karigor.friends.view.adapter.FriendsAdapter
import org.karigor.friends.view.viewmodel.FriendsViewModel
import org.karigor.friends.dto.Result

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : FriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[FriendsViewModel::class.java]
        getFriends()
        configSwipeToRefresh()
    }

    private fun configSwipeToRefresh(){
        binding.swipeRefreshLayout.setOnRefreshListener {
            getFriends()
        }
    }

    private fun getFriends(){
        binding.progressLayout.visibility = View.VISIBLE
        viewModel.getFriends().observe(this){
            val results = it.results ?: mutableListOf()
            setFriendsRecyclerView(results)
            if(results.isEmpty()){
                binding.ivNoData.visibility = View.VISIBLE
            }else{
                binding.ivNoData.visibility = View.GONE
            }
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressLayout.visibility = View.GONE
        }
    }

    private fun setFriendsRecyclerView(results : MutableList<Result>){
        val friendsAdapter = FriendsAdapter(results, object : FriendsAdapter.FriendsListener{
            override fun onClickItem(result: Result) {
                showCharacterDetails(result)
            }
        })
        binding.rvFriends.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = friendsAdapter
        }
    }

    private fun showCharacterDetails(result: Result) {
        val builder = AlertDialog.Builder(this)
        val dialogBinding = DialogFullInfoBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val dialog = builder.create()

        val salutation = result.name?.title ?: ""
        val firstName = result.name?.first ?: ""
        val lastName = result.name?.last ?: ""

        val fullName = "$salutation $firstName $lastName"
        val streetNo = result.location?.street?.number
        val streetName = result.location?.street?.name
        val country = result.location?.country
        val city = result.location?.city
        val state =  result.location?.state
        val address = "$streetNo, $streetName, $city, $state, $country"
        val imgUrl = result.picture?.large
        val email = result.email
        val phn = result.cell

        dialogBinding.apply {
            Glide.with(applicationContext).load(imgUrl).error(R.drawable.ic_user).apply(RequestOptions.circleCropTransform()).into(ivCharacterImg)
            tvName.text = fullName
            tvCountry.text = country
            tvAddress.text = address
            tvCity.text = city
            tvState.text = state
            tvEmail.text = email
            tvCell.text = phn

            btnClose.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

}