package infor.c14220016.latihansp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapterRecView(private val listtask: ArrayList<task>): RecyclerView
.Adapter<adapterRecView.ListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterRecView.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterRecView.ListViewHolder, position: Int) {
        var task: task = listtask[position]

        holder._namaTask.setText(task.nama)
        holder._deskripsiTask.setText(task.deskripsi)
        holder._tanggalTask.setText(task.tanggal.toString())
        holder._kategoriTask.setText(task.kategori)
        holder._kerjakanBtn.setText(task.status)

        if (holder._kerjakanBtn.text == "Done"){
            holder._kerjakanBtn.setBackgroundColor(Color.GRAY)
//            holder._hapusBtn.setBackgroundColor(Color.GRAY)
            holder._ubahBtn.setBackgroundColor(Color.GRAY)

            holder._kerjakanBtn.isEnabled = false
//            holder._hapusBtn.isEnabled = false
            holder._ubahBtn.isEnabled = false
        }

        holder._hapusBtn.setOnClickListener {
            onItemClickCallback.delData(position)
        }

        holder._kerjakanBtn.setOnClickListener{
            onItemClickCallback.dataProgress(position)
        }
    }

    override fun getItemCount(): Int {
        return listtask.size
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
//        fun onItemClicked(data:task)
        fun dataProgress(pos:Int)
        fun delData(pos:Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var _namaTask = itemView.findViewById<TextView>(R.id.namaTask)
        var _tanggalTask = itemView.findViewById<TextView>(R.id.tanggalTask)
        var _kategoriTask = itemView.findViewById<TextView>(R.id.kategoriTask)
        var _deskripsiTask = itemView.findViewById<TextView>(R.id.deskripsiTask)

        var _hapusBtn = itemView.findViewById<Button>(R.id.delBtn)
        var _ubahBtn = itemView.findViewById<Button>(R.id.editBtn)
        var _kerjakanBtn = itemView.findViewById<Button>(R.id.actionBtn)
    }
}