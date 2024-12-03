package infor.c14220016.latihansp

import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.util.Date
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {

    private var _nama: MutableList<String> = emptyList<String>().toMutableList()
    private var _tanggal: MutableList<Date> = emptyList<Date>().toMutableList()
    private var _kategori: MutableList<String> = emptyList<String>().toMutableList()
    private var _deskripsi: MutableList<String> = emptyList<String>().toMutableList()
    private var _status: MutableList<String> = emptyList<String>().toMutableList()

    lateinit var sp: SharedPreferences
    private lateinit var _rvTask: RecyclerView
    private var arTask= arrayListOf<task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _rvTask = findViewById<RecyclerView>(R.id.rvTasks)
        sp = getSharedPreferences("dataSP", MODE_PRIVATE)
        val gson = Gson()
        val isiSP = sp.getString("spTask", null)
        val type = object : TypeToken<ArrayList<task>>() {}.type
        if(isiSP != null){
            arTask = gson.fromJson(isiSP, type)
        }

        if (arTask.size == 0){
            siapkanData()
        } else {
            arTask.forEach{
                _nama.add(it.nama)
                _tanggal.add(it.tanggal)
                _kategori.add(it.kategori)
                _deskripsi.add(it.deskripsi)
                _status.add(it.status)
            }
            arTask.clear()
        }

        tambahData()
        tampilkanData()
    }

    fun siapkanData(){
        _nama = mutableListOf("Name 1", "Name 2", "Name 3")
        _tanggal = mutableListOf(
            Date(2024 - 1900, 0, 1),
            Date(2024 - 1900, 1, 14),
            Date(2024 - 1900, 2, 21)
        )
        _kategori = mutableListOf("Kat 1", "Kat 2", "Kat 3")
        _deskripsi = mutableListOf("Des 1", "Des 2", "Des 3")
        _status = mutableListOf("Kerjakan", "Kerjakan", "Kerjakan")
    }

    fun tambahData(){
        val gson = Gson()
        val editor = sp.edit()
        arTask.clear()
        for (position: Int in _nama.indices){
            val data = task(
                _nama[position],
                _tanggal[position],
                _kategori[position],
                _deskripsi[position],
                _status[position]
            )
            arTask.add(data)
        }
        val json = gson.toJson(arTask)
        editor.putString("spTask", json)
        editor.apply()
    }

    fun tampilkanData(){
        _rvTask.layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        val adapterTask = adapterRecView(arTask)
        _rvTask.adapter = adapterTask

        adapterTask.setOnItemClickCallback(object: adapterRecView.OnItemClickCallback{
            override fun dataProgress(pos: Int) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("KERJAKAN TASK")
                    .setMessage("Apakah Benar Data " + _nama[pos]+" akan diprogress ?")
                    .setPositiveButton(
                        "PROGRESS",
                        DialogInterface.OnClickListener { dialog, which ->

                            if (_status.get(pos) == "Kerjakan") {
                                _status.set(pos, "Selesaikan")
                            } else if (_status.get(pos) == "Selesaikan") {
                                _status.set(pos, "Done")
                            }

                            tambahData()
                            tampilkanData()
                        }
                    )
                    .setNegativeButton(
                        "BATAL",
                        DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(
                                this@MainActivity,
                                "Data Batal Dihapus",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    ).show()
            }

            override fun delData(pos: Int) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("HAPUS DATA")
                    .setMessage("Apakah Benar Data " + _nama[pos]+" akan dihapus ?")
                    .setPositiveButton(
                        "HAPUS",
                        DialogInterface.OnClickListener { dialog, which ->
                            _nama.removeAt(pos)
                            _tanggal.removeAt(pos)
                            _kategori.removeAt(pos)
                            _deskripsi.removeAt(pos)
                            _status.removeAt(pos)
                            tambahData()
                            tampilkanData()
                        }
                    )
                    .setNegativeButton(
                        "BATAL",
                        DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(
                                this@MainActivity,
                                "Data Batal Dihapus",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    ).show()
            }
        })
    }
}