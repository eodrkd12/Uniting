package com.uniting.android.Class

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.uniting.android.R
import java.text.SimpleDateFormat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.uniting.android.Chat.ChatActivity
import com.uniting.android.DB.Entity.Chat
import com.uniting.android.DB.Entity.Room
import com.uniting.android.DB.ViewModel.ChatViewModel
import com.uniting.android.Home.ConditionAdapter
import com.uniting.android.Login.UserItem
import com.uniting.android.Login.UserOptionAdapter
import com.uniting.android.Option.PersonalityAdapter
import com.uniting.android.Room.MakeRoomActivity
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.dialog_inquire.*
import java.util.*
import java.util.concurrent.locks.Condition
import kotlin.collections.ArrayList

class PSDialog(activity: Activity) {

    var context : Activity? = null
    var dialog : Dialog? = null

    init {
        dialog = Dialog(activity)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        context =activity
    }

    fun show() {
        if(dialog != null) {
            dialog!!.show()
        }
    }

    fun dismiss() {
        if(dialog != null) {
            dialog!!.dismiss()
        }
    }

    interface SaveBtnClickListener {
        fun onClick(userOption : String)
    }

    private lateinit var saveBtnClickListener : SaveBtnClickListener

    fun setSaveBtnClickListener(saveBtnClickListener: SaveBtnClickListener) {
        this.saveBtnClickListener = saveBtnClickListener
    }

    fun curDate() : String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var curDate = simpleDateFormat.format(System.currentTimeMillis())

        return curDate
    }

    fun setUserOption(title : String, userOptionList: ArrayList<UserItem.UserOption>) {
        dialog = Dialog(context!!, R.style.popCasterDlgTheme)
        val dialogView = context!!.layoutInflater.inflate(R.layout.dialog_edit_condition, null)
        var titleText : TextView = dialogView.findViewById(R.id.text_condition_title)
        var userOptionRV: RecyclerView = dialogView.findViewById(R.id.rv_condition)
        var selectedOption = ""
        var saveBtn: Button = dialogView.findViewById(R.id.btn_save)

        dialog!!.getWindow()!!.getAttributes().windowAnimations = R.style.DialogSlideRight
        dialog!!.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))


        var ssb: SpannableStringBuilder = SpannableStringBuilder(title + "을 알려주세요.")
        ssb.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleText.text = ssb

        if(title == "성격" || title == "취미") {
            userOptionRV.setHasFixedSize(true)
            userOptionRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            userOptionRV.adapter = PersonalityAdapter(context!!, userOptionList)
        } else {
            userOptionRV.setHasFixedSize(true)
            userOptionRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            userOptionRV.adapter = UserOptionAdapter(context!!, userOptionList)
        }

        saveBtn.setOnClickListener {
            for(i in userOptionList) {
                if(i.isSelected) {
                    selectedOption += i.title + ","
                }
            }

            if(selectedOption == "") {
                Toast.makeText(context, "선택된 항목이 없습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                saveBtnClickListener.onClick(selectedOption)
                dismiss()
            }

        }
    }

    fun setMatchingChange(vpMatching : ViewPager2) {
        dialog!!.setContentView(R.layout.dialog_matching_change)

        var btnCancel = dialog!!.findViewById<Button>(R.id.btn_cancel)
        var btnChange = dialog!!.findViewById<Button>(R.id.btn_change)

        btnCancel.setOnClickListener {
            dismiss()
        }
        btnChange.setOnClickListener {
            vpMatching.currentItem=1
            dismiss()
        }
    }

    fun setMatchingCondition(title : String, conditionList: ArrayList<String>, textView: TextView){
        dialog = Dialog(context!!, R.style.popCasterDlgTheme)
        var view = context!!.layoutInflater.inflate(R.layout.dialog_edit_condition, null)

        var textTitle = view.findViewById<TextView>(R.id.text_condition_title)
        var rvCondition = view.findViewById<RecyclerView>(R.id.rv_condition)
        var btnSave = view.findViewById<Button>(R.id.btn_save)

        textTitle.text = title
        dialog!!.window!!.attributes.windowAnimations = R.style.DialogSlideRight
        dialog!!.addContentView(view, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))

        rvCondition.setHasFixedSize(true)
        rvCondition.layoutManager=LinearLayoutManager(context!!,RecyclerView.VERTICAL,false)
        rvCondition.adapter=ConditionAdapter(context!!, conditionList)


        btnSave.setOnClickListener {
            if(title.contains("키") || title.contains("나이")){
                if(ConditionAdapter.selectedCondition.split(", ").size != 2){
                    Toast.makeText(context, "두 가지를 선택해주세요.",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else{
                    var array = ConditionAdapter.selectedCondition.split(", ")
                    array = array.sorted()
                    textView.text = "${array[0]} ~ ${array[1]}"
                    ConditionAdapter.selectedCondition = ""
                    dismiss()
                }
            }
            else {
                textView.setText(ConditionAdapter.selectedCondition)
                ConditionAdapter.selectedCondition = ""
                dismiss()
            }
        }
    }

    class BottomSheetDialog(var textView: TextView) : BottomSheetDialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            return inflater.inflate(R.layout.dialog_category, container, false)
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            view?.findViewById<TextView>(R.id.text_hobby)?.setOnClickListener {
                MakeRoomActivity.category="취미"
                MakeRoomActivity.textNext!!.setTextColor(Color.WHITE)
                MakeRoomActivity.textNext!!.isEnabled=true
                textView.text = "취미"
                textView.setTextColor(Color.WHITE)
                dismiss()
            }

            view?.findViewById<TextView>(R.id.text_study)?.setOnClickListener {
                MakeRoomActivity.category="스터디"
                MakeRoomActivity.textNext!!.setTextColor(Color.WHITE)
                MakeRoomActivity.textNext!!.isEnabled=true
                textView.text = "스터디"
                textView.setTextColor(Color.WHITE)
                dismiss()
            }

            view?.findViewById<TextView>(R.id.text_counsel)?.setOnClickListener {
                MakeRoomActivity.category="고민상담"
                MakeRoomActivity.textNext!!.setTextColor(Color.WHITE)
                MakeRoomActivity.textNext!!.isEnabled=true
                textView.text = "고민상담"
                textView.setTextColor(Color.WHITE)
                dismiss()
            }

            view?.findViewById<TextView>(R.id.text_talk)?.setOnClickListener {
                MakeRoomActivity.category="잡담"
                MakeRoomActivity.textNext!!.setTextColor(Color.WHITE)
                MakeRoomActivity.textNext!!.isEnabled=true
                textView.text = "잡담"
                textView.setTextColor(Color.WHITE)
                dismiss()
            }
        }
    }

    fun setCheckSave() {
        dialog!!.setContentView(R.layout.dialog_check_save)

        val titleText : TextView = dialog!!.findViewById(R.id.text_dialogtitle)
        val contentText : TextView = dialog!!.findViewById(R.id.text_dialogcontent)
        val btnCancel : Button = dialog!!.findViewById(R.id.btn_dialogcancel)
        val btnAccept : Button = dialog!!.findViewById(R.id.btn_dialogaccept)

        titleText.text = "경고"
        contentText.text = "변경사항이 저장되지 않습니다.\n진행하시겠습니까?"

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnAccept.setOnClickListener {
            dismiss()
            context?.finish()
        }
    }

    fun setBlocking(blockingSwitch : Switch) {
        dialog!!.setContentView(R.layout.dialog_check_save)

        val titleText : TextView = dialog!!.findViewById(R.id.text_dialogtitle)
        val contentText : TextView = dialog!!.findViewById(R.id.text_dialogcontent)
        val btnCancel : Button = dialog!!.findViewById(R.id.btn_dialogcancel)
        val btnAccept : Button = dialog!!.findViewById(R.id.btn_dialogaccept)

        titleText.text = "같은 학과 차단"
        contentText.text = "같은 학과 유저가 매칭되지않습니다.\n(오픈채팅제외)\n진행하시겠습니까?"

        btnCancel.setOnClickListener {
            blockingSwitch.isChecked = false
            dismiss()
        }

        btnAccept.setOnClickListener {
            blockingSwitch.isChecked = true
            dismiss()
            Retrofit.updateBlocking(UserInfo.ID, UserInfo.DEPT, curDate(), "insert") {
                if(it.result != "success") {
                    Log.d("test", "학과차단오류")
                    blockingSwitch.isChecked = false
                }
                else {
                    UserInfo.BLOCKINGDEPT = 1
                    Log.d("test", "학과차단완료")
                }
            }
        }
    }

    fun setInquireClickDialog(title: String, content: String, answer: String, date:String, answerDate: String) {
        dialog = Dialog(context!!, R.style.popCasterDlgTheme)
        val dialogView = context!!.layoutInflater.inflate(R.layout.dialog_inquire_click, null)
        val titleText : TextView = dialogView.findViewById(R.id.text_inquireclicktitle)
        val contentText : TextView = dialogView.findViewById(R.id.text_inquireclickcontent)
        val answerText : TextView = dialogView.findViewById(R.id.text_inquireclickanswer)
        val cancelBtn : ImageView = dialogView.findViewById(R.id.image_inquireclickcancel)
        val dateText : TextView = dialogView.findViewById(R.id.text_inquireclickdate)
        val answerDateText: TextView = dialogView.findViewById(R.id.text_inquireclickanswerdate)

        dateText.text = date
        answerDateText.text = answerDate
        titleText.text = title
        contentText.text = content
        answerText.text = answer

        dialog!!.getWindow()!!.getAttributes().windowAnimations = R.style.DialogSlideRight
        dialog!!.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }

    class InquireBottomSheetDialog(categoryText : TextView) : BottomSheetDialogFragment() {
        var categoryText = categoryText

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            return inflater.inflate(R.layout.dialog_inquire, container, false)
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            view?.findViewById<TextView>(R.id.text_inquirepurchase)?.setOnClickListener {
                categoryText.text = text_inquirepurchase.text
                dismiss()
            }

            view?.findViewById<TextView>(R.id.text_inquireservice)?.setOnClickListener {
                categoryText.text = text_inquireservice.text
                dismiss()
            }
        }
    }

    fun setIntroduce() {
        dialog = Dialog(context!!, R.style.popCasterDlgTheme)
        val dialogView = context!!.layoutInflater.inflate(R.layout.dialog_introduce, null)
        val acceptBtn : Button = dialogView.findViewById(R.id.btn_introduce_accept)
        val backBtn : ImageButton = dialogView.findViewById(R.id.btn_introduce_back)
        val introduceEdit : EditText = dialogView.findViewById(R.id.edit_introduce)

        dialog!!.getWindow()!!.getAttributes().windowAnimations = R.style.DialogSlideRight
        dialog!!.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))

        acceptBtn.setOnClickListener {
            if(introduceEdit.text.length < 10) {
                Toast.makeText(context, "10자 이상 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                saveBtnClickListener.onClick(introduceEdit.text.toString())
                dismiss()
            }
        }

        backBtn.setOnClickListener {
            dismiss()
        }

    }

    fun setChat(userId: String, userNickname: String) {
        dialog!!.setContentView(R.layout.dialog_check_save)

        val titleText : TextView = dialog!!.findViewById(R.id.text_dialogtitle)
        val contentText : TextView = dialog!!.findViewById(R.id.text_dialogcontent)
        val btnCancel : Button = dialog!!.findViewById(R.id.btn_dialogcancel)
        val btnAccept : Button = dialog!!.findViewById(R.id.btn_dialogaccept)

        titleText.text = "대화 신청"
        contentText.text = "${userNickname}님과의\n대화를 진행하시겠습니까?"

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnAccept.setOnClickListener {

            var roomId = ""

            for(i in 0..11){
                roomId += Random().nextInt(10).toString()
            }

            Retrofit.createRoom(roomId,"${UserInfo.NICKNAME}&${userNickname}","데이팅",curDate(),"",UserInfo.UNIV,UserInfo.ID){
                if(it.result=="success"){
                    Retrofit.joinRoom(roomId, userId, curDate()){
                        Retrofit.subscribeFcm(roomId,userId){

                        }
                    }
                    Retrofit.joinRoom(roomId, UserInfo.ID, curDate()){
                        if(it.result=="success"){
                            var room = Room(
                                roomId,
                                "${UserInfo.NICKNAME}&${userNickname}",
                                UserInfo.ID,
                                "데이팅",
                                curDate(),
                                "",
                                UserInfo.UNIV
                            )

                            val calendar = Calendar.getInstance()
                            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                            var dayOfWeekStr = ""

                            when(dayOfWeek) {
                                1 -> {
                                    dayOfWeekStr = "일요일"
                                }
                                2 -> {
                                    dayOfWeekStr = "월요일"
                                }
                                3 -> {
                                    dayOfWeekStr = "화요일"
                                }
                                4 -> {
                                    dayOfWeekStr = "수요일"
                                }
                                5 -> {
                                    dayOfWeekStr = "목요일"
                                }
                                6 -> {
                                    dayOfWeekStr = "금요일"
                                }
                                7 -> {
                                    dayOfWeekStr = "토요일"
                                }
                            }

                            var systemChat = Chat(
                                "SYSTEM_${room.room_id}_${curDate()}",
                                room.room_id,
                                "SYSTEM",
                                "SYSTEM",
                                "${curDate().split(" ")[0]} ${dayOfWeekStr}",
                                curDate(), "", 1
                            )

                            var chatViewModel = ChatViewModel(context!!.application, roomId)

                            Retrofit.insertChat(systemChat){
                                if(it.result == "success"){
                                    writeFirebase(systemChat, roomId)
                                    chatViewModel.insert(systemChat){
                                        var intent = Intent(context, ChatActivity::class.java)
                                        FirebaseMessaging.getInstance().subscribeToTopic(room.room_id)
                                        intent.putExtra("room",room)
                                        intent.putExtra("last_chat_time","0000-00-00")
                                        context!!.startActivity(intent)
                                        context!!.finish()
                                        dismiss()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun writeFirebase(chat: Chat, roomId: String){

        var ref = FirebaseDatabase.getInstance().reference.child("chat").child(roomId)

        var map = HashMap<String, Any>()
        val key: String? = ref!!.push().key

        var root = ref!!.child(key!!)
        var objectMap = HashMap<String, Any>()

        objectMap.put("chat_id", chat.chat_id)
        objectMap.put("room_id", chat.room_id)
        objectMap.put("user_id", chat.user_id)
        objectMap.put("user_nickname", chat.user_nickname)
        objectMap.put("chat_content", chat.chat_content)
        objectMap.put("chat_time", chat.chat_time)
        objectMap.put("unread_member", chat.unread_member)
        objectMap.put("system_chat", chat.system_chat)

        root.updateChildren(objectMap)
    }
}