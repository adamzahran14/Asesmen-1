package org.d3if2084.tipcalculatorapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import org.d3if2084.tipcalculatorapp.databinding.ActivityMainBinding

private const val TAG ="MainActivity"
private const val INITIAL_TIP_PERCENT = 15
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var jumlahBill : EditText
    private lateinit var seekBarTip : SeekBar
    private lateinit var persen : TextView
    private lateinit var hasilJumlahTip : TextView
    private lateinit var hasilTotalHarga : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jumlahBill = findViewById(R.id.jumlahBill)
        seekBarTip = findViewById(R.id.seekBarTip)
        persen = findViewById(R.id.persen)
        hasilJumlahTip = findViewById(R.id.hasilJumlahTip)
        hasilTotalHarga = findViewById(R.id.hasilTotalHarga)

        binding.apply {
            button.setOnClickListener {
                hitungTipDanTotal()
            }
        }



        //menyimpan posisi seekbar ke tengah, sehingga persentase nya hanya 15%
        seekBarTip.progress = INITIAL_TIP_PERCENT
        persen.text = "$INITIAL_TIP_PERCENT%";

        //menambah listener ke seekbar
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                //logcat info
                Log.i(TAG, "onProgressChanged $p1")
                persen.text = "$p1%";
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
        //menambah listener ke edittext
        jumlahBill.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                hitungTipDanTotal()
            }

        })
    }

    private fun hitungTipDanTotal() {
        //mendapatkan hasil dari jumlah bill dan tip persen
        val bill = binding.jumlahBill.text.toString()
        if (TextUtils.isEmpty(bill)) {
            Toast.makeText(this, R.string.bill_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tipPersen = binding.seekBarTip.progress

        //menghitung tip dan total
        val jumlahTip = bill.toFloat() * tipPersen / 100
        val totalHarga = bill.toFloat() + jumlahTip

        //Update UI
        binding.hasilJumlahTip.text = jumlahTip.toString()
        binding.hasilTotalHarga.text = totalHarga.toString()
    }
}