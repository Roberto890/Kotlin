package com.example.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.R
import com.example.convidados.service.constants.GuestConstants
import com.example.convidados.viewmodel.GuestFormViewModel
import kotlinx.android.synthetic.main.activity_guest_form.*

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: GuestFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_form)

        mViewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        loadData()
        setListeners()
        observe()
    }

    override fun onClick(v: View) {
        //Pega o id se for o burron save que foi clicado ele faz o que ta no if
        val id = v.id
        if (id == R.id.button_save) {
            val name = edit_name.text.toString()
            val presence = radio_presence.isChecked
            mViewModel.save(name, presence)
        }
    }

    private fun observe(){
        mViewModel.saveGuest.observe(this, Observer {
            if (it == true){
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        mViewModel.guest.observe(this, Observer {
            edit_name.setText(it.name)
            if (it.presence){
                radio_presence.isChecked = true
            } else{
                radio_absent.isChecked = true
            }
        })
    }

    private fun setListeners() {
        //Passando nossa activity quando clicar
        findViewById<Button>(R.id.button_save).setOnClickListener(this)
    }

    private fun loadData(){
        val bundle = intent.extras
        if (bundle != null){
            val id = bundle.getInt(GuestConstants.GUESTID)
            mViewModel.load(id)
        }
    }

}