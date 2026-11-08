package com.example.lab8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private val data: ArrayList<Contact>
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    // 目前顯示的聯絡人
    private val filteredData = ArrayList(data)

    // 記錄目前的搜尋文字
    private var currentQuery = ""

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val tvName: TextView = v.findViewById(R.id.tvName)
        private val tvPhone: TextView = v.findViewById(R.id.tvPhone)
        private val tvEmail: TextView = v.findViewById(R.id.tvEmail)
        private val imgDelete: ImageView = v.findViewById(R.id.imgDelete)

        fun bind(
            item: Contact,
            clickListener: (Contact) -> Unit
        ) {
            tvName.text = item.name
            tvPhone.text = item.phone
            tvEmail.text = item.email

            imgDelete.setOnClickListener {
                clickListener.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        position: Int
    ): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_row, viewGroup, false)

        return ViewHolder(v)
    }

    override fun getItemCount() = filteredData.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(filteredData[position]) { item ->
            data.remove(item)
            filterContacts(currentQuery)
        }
    }

    // 搜尋姓名、電話及 Email
    fun filterContacts(query: String) {
        currentQuery = query.trim()

        filteredData.clear()

        if (currentQuery.isEmpty()) {
            filteredData.addAll(data)
        } else {
            filteredData.addAll(
                data.filter { contact ->
                    contact.name.contains(
                        currentQuery,
                        ignoreCase = true
                    ) ||
                            contact.phone.contains(
                                currentQuery,
                                ignoreCase = true
                            ) ||
                            contact.email.contains(
                                currentQuery,
                                ignoreCase = true
                            )
                }
            )
        }

        notifyDataSetChanged()
    }
}
