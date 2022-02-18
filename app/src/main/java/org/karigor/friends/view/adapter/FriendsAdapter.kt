package org.karigor.friends.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.karigor.friends.R
import org.karigor.friends.databinding.ItemFriendsBinding
import org.karigor.friends.dto.Result

class FriendsAdapter(
    private val friends: MutableList<Result>,
    private val listener: FriendsListener
) : RecyclerView.Adapter<FriendsAdapter.FriendsView>() {

    inner class FriendsView(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemFriendsBinding.bind(itemView)
    }

    interface FriendsListener{
        fun onClickItem(result : Result)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friends, parent, false)
        return FriendsView(view)
    }

    override fun onBindViewHolder(holder: FriendsView, position: Int) {
        val result = friends[position]
        val salutation = result.name?.title ?: ""
        val firstName = result.name?.first ?: ""
        val lastName = result.name?.last ?: ""

        val fullName = "$salutation $firstName $lastName"
        val country = result.location?.country
        val imgUrl = result.picture?.medium

        holder.binding.apply {
            tvName.text = fullName
            tvCountry.text = country
            Glide.with(ivUserImg.context)
                .load(imgUrl)
                .error(R.drawable.ic_user)
                .apply(RequestOptions.circleCropTransform())
                .into(ivUserImg)

            root.setOnClickListener { listener.onClickItem(result)}
        }
    }

    override fun getItemCount(): Int {
        return friends.size
    }

}