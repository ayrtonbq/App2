package com.example.app2

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import android.text.TextWatcher
import android.widget.EditText
import android.text.Editable

class MainActivity : AppCompatActivity() {

    private lateinit var etNome: EditText
    private lateinit var etDataNascimento: EditText
    private lateinit var etEmail: EditText
    private lateinit var etTelefone: EditText
    private lateinit var rgSatisfacao: RadioGroup
    private lateinit var rgIndicacao: RadioGroup
    private lateinit var rgUtilidade: RadioGroup
    private lateinit var cbRobustez: CheckBox
    private lateinit var cbAgilidade: CheckBox
    private lateinit var cbInovacao: CheckBox
    private lateinit var cbLentidao: CheckBox
    private lateinit var cbIneficiencia: CheckBox
    private lateinit var cbProdutividade: CheckBox
    private lateinit var cbBurocracia: CheckBox
    private lateinit var btnConfirmar: Button
    private lateinit var btnVoltar: Button
    private lateinit var btnFinalizar: Button
    private lateinit var layoutPrincipal: LinearLayout
    private lateinit var layoutConfirmacao: LinearLayout
    private lateinit var tvRespostas: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNome = findViewById(R.id.etNome)
        etDataNascimento = findViewById(R.id.etDataNascimento)
        etEmail = findViewById(R.id.etEmail)
        etTelefone = findViewById(R.id.etTelefone)
        rgSatisfacao = findViewById(R.id.rgSatisfacao)
        rgIndicacao = findViewById(R.id.rgIndicacao)
        rgUtilidade = findViewById(R.id.rgUtilidade)
        cbRobustez = findViewById(R.id.cbRobustez)
        cbAgilidade = findViewById(R.id.cbAgilidade)
        cbInovacao = findViewById(R.id.cbInovacao)
        cbLentidao = findViewById(R.id.cbLentidao)
        cbIneficiencia = findViewById(R.id.cbIneficiencia)
        cbProdutividade = findViewById(R.id.cbProdutividade)
        cbBurocracia = findViewById(R.id.cbBurocracia)
        btnConfirmar = findViewById(R.id.btnConfirmar)
        btnVoltar = findViewById(R.id.btnVoltar)
        btnFinalizar = findViewById(R.id.btnFinalizar)
        layoutPrincipal = findViewById(R.id.layoutPrincipal)
        layoutConfirmacao = findViewById(R.id.layoutConfirmacao)
        tvRespostas = findViewById(R.id.tvRespostas)

        etDataNascimento.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                    etDataNascimento.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
                }, year, month, day
            )

            datePickerDialog.show()
        }


        val etTelefone: EditText = findViewById(R.id.etTelefone)
        etTelefone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val original = s.toString().replace("\\D".toRegex(), "")
                val formatted = when {
                    original.length > 6 -> "${original.substring(0, 2)} ${
                        original.substring(
                            2,
                            6
                        )
                    }-${original.substring(6)}"

                    original.length > 2 -> "${original.substring(0, 2)} ${original.substring(2)}"
                    else -> original
                }
                etTelefone.removeTextChangedListener(this)
                etTelefone.setText(formatted)
                etTelefone.setSelection(formatted.length)
                etTelefone.addTextChangedListener(this)
            }
        })

        btnConfirmar.setOnClickListener {
            val respostas = """
        Nome: ${etNome.text}

        Data de Nascimento: ${etDataNascimento.text}

        Email: ${etEmail.text}

        Telefone: ${etTelefone.text}

        Grau de Satisfação: ${getSelectedRadioButtonText(rgSatisfacao)}

        Indicaria o Produto: ${getSelectedRadioButtonText(rgIndicacao)}

        Grau de Utilidade: ${getSelectedRadioButtonText(rgUtilidade)}

        Opiniões sobre o produto: ${getSelectedCheckBoxText()}
        """.trimIndent()

            tvRespostas.text = respostas
            layoutPrincipal.visibility = View.GONE
            btnConfirmar.visibility = View.GONE
            layoutConfirmacao.visibility = View.VISIBLE
        }


        btnVoltar.setOnClickListener {
            layoutPrincipal.visibility = View.VISIBLE
            btnConfirmar.visibility = View.VISIBLE
            layoutConfirmacao.visibility = View.GONE
        }

        btnFinalizar.setOnClickListener {
            finish()
        }
    }

    private fun getSelectedRadioButtonText(radioGroup: RadioGroup): String {
        val selectedId = radioGroup.checkedRadioButtonId
        return if (selectedId != -1) {
            val radioButton = findViewById<RadioButton>(selectedId)
            radioButton.text.toString()
        } else {
            ""
        }
    }

    private fun getSelectedCheckBoxText(): String {
        val result = StringBuilder()
        if (cbRobustez.isChecked) result.append(cbRobustez.text).append(", ")
        if (cbAgilidade.isChecked) result.append(cbAgilidade.text).append(", ")
        if (cbInovacao.isChecked) result.append(cbInovacao.text).append(", ")
        if (cbLentidao.isChecked) result.append(cbLentidao.text).append(", ")
        if (cbIneficiencia.isChecked) result.append(cbIneficiencia.text).append(", ")
        if (cbProdutividade.isChecked) result.append(cbProdutividade.text).append(", ")
        if (cbBurocracia.isChecked) result.append(cbBurocracia.text).append(", ")

        if (result.isNotEmpty()) {
            result.setLength(result.length - 2)
        }
        return result.toString()
    }
}