package com.uniting.android.Login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniting.android.Class.KeyboardVisibilityUtils
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_signup1.*
import org.json.JSONObject

class Signup1Activity : AppCompatActivity() {
    var univName = ""
    var univMail = ""
    var deptName = ""

    var univList : ArrayList<UniversityItem.University> = arrayListOf()
    var univFilter : ArrayList<UniversityItem.University> = arrayListOf()
    var deptList : ArrayList<UniversityItem.Department> = arrayListOf()
    var deptFilter : ArrayList<UniversityItem.Department> = arrayListOf()

    lateinit var imm : InputMethodManager

    private lateinit var keyboardVisibilityUtils : KeyboardVisibilityUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup1)

        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        keyboardVisibilityUtils = KeyboardVisibilityUtils(window, onShowKeyboard = {keyboardHeight ->
            scroll_signup1.run {
                smoothScrollTo(scrollX, scrollY + keyboardHeight)
            }
        })

        Retrofit.getUniversity {
            univList = it

            edit_university.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(univList.size == 0)
                    {
                        //Toast.makeText(this@Signup1Activity, "없음", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        univFilter.clear()
                        for(i in 0..univList.size-1) {
                            if((univList.get(i).univName!!.contains(edit_university.text) == true) && edit_university.text.toString() != "") {
                                univFilter.add(univList.get(i))
                            }
                        }

                        val univAdapter = UniversityAdapter(this@Signup1Activity, univFilter)

                        univAdapter.setItemClickListener(object: UniversityAdapter.ItemClickListener {
                            override fun onClick(name: String, mail: String) {
                                univName = name
                                univMail = mail

                                edit_university.setText(name)
                                edit_university.isCursorVisible = false
                                rv_university.adapter = null
                                imm.hideSoftInputFromWindow(edit_university.windowToken, 0)

                                Retrofit.getDepartment(name) {
                                    deptList = it

                                    edit_department.addTextChangedListener(object : TextWatcher {
                                        override fun afterTextChanged(p0: Editable?) {

                                        }

                                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                                        }

                                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                                            if(deptList.size == 0)
                                            {
                                                //Toast.makeText(this@Signup1Activity, "없음", Toast.LENGTH_SHORT).show()
                                            }
                                            else{
                                                deptFilter.clear()
                                                for(i in 0..deptList.size-1) {
                                                    if((deptList.get(i).deptName.contains(edit_department.text) == true) && edit_department.text.toString() != "") {
                                                        deptFilter.add(deptList.get(i))
                                                    }
                                                }

                                                val deptAdapter = DepartmentAdapter(this@Signup1Activity, deptFilter)

                                                deptAdapter.setItemClickListener(object: DepartmentAdapter.ItemClickListener {
                                                    override fun onClick(name: String) {
                                                        deptName = name
                                                        edit_department.setText(name)
                                                        edit_department.isCursorVisible = false
                                                        btn_signup1.isEnabled = true
                                                        rv_department.adapter = null
                                                        imm.hideSoftInputFromWindow(edit_department.windowToken, 0)
                                                    }
                                                })

                                                rv_department.setHasFixedSize(true)
                                                rv_department.layoutManager = LinearLayoutManager(this@Signup1Activity, RecyclerView.VERTICAL, false)
                                                rv_department.adapter = deptAdapter
                                            }
                                        }
                                    })

                                }

                            }
                        })

                        rv_university.setHasFixedSize(true)
                        rv_university.layoutManager = LinearLayoutManager(this@Signup1Activity, RecyclerView.VERTICAL, false)
                        rv_university.adapter = univAdapter
                    }
                }
            })
        }


        edit_university.setOnTouchListener{ view, event->
            when(event.action) {
                MotionEvent.ACTION_DOWN-> {
                    edit_university.setFocusable(true)
                    edit_university.setText(null)
                    edit_university.setCursorVisible(true)
                    edit_department.setText(null)
                    btn_signup1.setEnabled(false)
                    deptList.clear()
                    deptFilter.clear()
                }
            }
            false
        }

        edit_department.setOnTouchListener{view, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN->{
                    edit_department.setCursorVisible(true)
                    edit_department.setText(null)
                    btn_signup1.setEnabled(false)
                }
            }
            false
        }

        btn_signup1.setOnClickListener {
            var intent = Intent(this, Signup2Activity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroy()
    }
}