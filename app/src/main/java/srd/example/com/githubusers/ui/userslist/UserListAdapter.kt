package srd.example.com.githubusers.ui.userslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_list_item.view.*
import srd.example.com.githubusers.R
import srd.example.com.githubusers.model.User

class UserListAdapter(var dataset: List<User> = emptyList(),private val itemSelected: UserSelected) :
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    class UserViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val textView = view.login
        val image = view.logo
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): UserListAdapter.UserViewHolder {
        val parentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_list_item, parent, false) as LinearLayout

        return UserViewHolder(parentView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = dataset[position]
        holder.textView.text = user.login
        if(user.imgUrl.toString().isNotEmpty()) {
            Picasso.get()
                .load(user.imgUrl.toString())
                .resize(AVATAR_SIZE, AVATAR_SIZE)
                .placeholder(android.R.drawable.ic_dialog_info)
                .error(android.R.drawable.ic_delete)
                .into(holder.image)
        }else{
            holder.image.setImageResource(android.R.drawable.ic_delete)
        }
        holder.view.setOnClickListener { itemSelected.onUserSelecter(dataset[position]) }
    }

    override fun getItemCount() = dataset.size

    companion object {
        private const val AVATAR_SIZE = 50
    }
}
