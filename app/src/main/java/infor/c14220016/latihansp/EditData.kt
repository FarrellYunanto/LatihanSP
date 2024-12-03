package infor.c14220016.latihansp

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date

class EditData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_data)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editData = intent.getParcelableExtra<task>("editData", task::class.java)
        val position = intent.getIntExtra("pos", -1)

        var _namaIn = findViewById<EditText>(R.id.namaInput)
        var _tanggalIn = findViewById<EditText>(R.id.tanggalInput)
        var _kategoriIn = findViewById<EditText>(R.id.kategoriInput)
        var _deskripsiIn = findViewById<EditText>(R.id.deskripsiInput)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val dateString = dateFormat.format(editData?.tanggal)

        _namaIn.setText(editData?.nama)
        _tanggalIn.setText(dateString)
        _kategoriIn.setText(editData?.kategori)
        _deskripsiIn.setText(editData?.deskripsi)

        var _confirmBtn = findViewById<Button>(R.id.confirmBtn)
        _confirmBtn.setOnClickListener {
            var tAwalF = _tanggalIn.text.toString().split("/")
            val _tanggal: Date = Calendar.getInstance().apply {
                set(Calendar.YEAR, tAwalF[2].toInt())
                set(Calendar.MONTH, tAwalF[1].toInt() - 1)
                set(Calendar.DAY_OF_MONTH, tAwalF[0].toInt())

                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time

            var data = task(
                _namaIn.text.toString(),
                _tanggal,
                _kategoriIn.text.toString(),
                _deskripsiIn.text.toString(),
                editData?.status.toString()
            )

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("editData", data)
            intent.putExtra("pos", position)

            startActivity(intent)
        }

    }
}