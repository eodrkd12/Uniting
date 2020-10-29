//
//  ChatView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/17.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import FirebaseDatabase

struct ChatView: View {
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @State var room: RoomData
    
    @State var ref : DatabaseReference?
    @State var query : DatabaseQuery?
    
    @State var chatDataList : [ChatData] = []
    @State var chatList : [ChatItem] = []
    
    @State var memberList : [MemberData] = []
    
    @State var menuVisible = false
    
    @State var height : CGFloat=0
    @State var content = ""
    
    @State var title = ""
    var body: some View {
        ZStack{
            VStack{
                VStack{
                    HStack{
                        Button(action: {
                            presentationMode.wrappedValue.dismiss()
                        }, label: {
                            Image("black_back_icon")
                                .resizable()
                                .frame(width: 20, height: 20)
                        })
                        
                        Spacer()
                        Text(title)
                            .font(.system(size: 20))
                            .foregroundColor(Colors.grey700)
                        Spacer()
                        Image("option_icon")
                            .resizable()
                            .frame(width: 20, height: 20)
                            .onTapGesture {
                                self.menuVisible.toggle()
                            }
                    }
                    ScrollView{
                        ForEach(chatList, id:\.chat.chat_id){ chat in
                            chat
                        }
                    }
                }
                .padding()
                Spacer()
                HStack(){
                    Spacer()
                    Text(content)
                        .opacity(0)
                        .font(.system(size: 18))
                        .frame(minWidth: UIScreen.main.bounds.width*0.7,maxWidth: UIScreen.main.bounds.width*0.7,minHeight: 25)
                        .padding(4)
                        .lineLimit(4)
                        .overlay(GeometryReader{ geometry in
                            ScrollView(showsIndicators: false){
                                TextEditor(text: $content)
                                    .frame(width: geometry.size.width, height: geometry.size.height)
                                    .padding(4)
                                    .font(.system(size: 18))
                                    .background(Colors.grey200)
                                    .overlay(
                                        RoundedRectangle(cornerRadius: 15)
                                            .stroke(Colors.grey300,lineWidth: 2)
                                    )
                            }
                        })
                    Spacer()
                    VStack{
                        Button(action: {
                            self.sendChat()
                        }){
                            Image("send_icon")
                                .resizable()
                                .padding(7)
                                .frame(width:38,height:38)
                                .background(Colors.primary)
                                .clipShape(Circle())
                        }
                        .padding(.top,7)
                    }
                    Spacer()
                }
                .padding(.bottom,23)
                .padding(.horizontal,15)
                .padding(.top,2)
                .background(Colors.grey100)
                .offset(y: -self.height)
            }
            GeometryReader{ _ in
                HStack{
                    Spacer()
                    RoomMenu(presentationMode: self.presentationMode, room: room, memberList: $memberList)
                        .offset(x: self.menuVisible ? 0 : UIScreen.main.bounds.width)
                        .animation(.easeIn(duration: 0.2))
                }
            }.background(Color.black.opacity(self.menuVisible ? 0.3 : 0))
            .padding(.top,50)
        }
        .navigationBarTitle("")
        .navigationBarHidden(true)
        .ignoresSafeArea(edges: .bottom)
        .onAppear(){
            
            NotificationCenter.default.addObserver(forName: UIResponder.keyboardWillShowNotification, object: nil, queue: .main) { noti in
                let value = noti.userInfo![UIResponder.keyboardFrameEndUserInfoKey] as! CGRect
                let height = value.height
                
                self.height=height
            }
            
            NotificationCenter.default.addObserver(forName: UIResponder.keyboardWillHideNotification, object: nil, queue: .main){ noti in
                self.height=0
            }
            
            self.ref = Database.database().reference().child("chat").child(self.room.room_id)
            self.query = self.ref!.queryOrdered(byChild: "chat_time")
            
            self.query?.observe(.childAdded, with: {(snapshot) in
                var key = snapshot.key
                var value = snapshot.value as! [String : Any?]
                
                self.chatDataList.append(ChatData(
                    chat_id: value["chat_id"].unsafelyUnwrapped as! String,
                    room_id: value["room_id"].unsafelyUnwrapped as! String,
                    user_id: value["user_id"].unsafelyUnwrapped as! String,
                    user_nickname: value["user_nickname"].unsafelyUnwrapped as! String,
                    chat_content: value["chat_content"].unsafelyUnwrapped as! String,
                    chat_time: value["chat_time"].unsafelyUnwrapped as! String,
                    unread_member: value["unread_member"].unsafelyUnwrapped as! String,
                    system_chat: value["system_chat"].unsafelyUnwrapped as! Int
                ))
                
                
                if self.chatDataList.count==1 || self.chatDataList[chatDataList.count-1].user_id != self.chatDataList[chatDataList.count-2].user_id {
                    self.chatList.append(ChatItem(
                                            chat: self.chatDataList[chatDataList.count-1],
                                            nicknameVisible: true,
                                            timeVisible: true))
                }
                else {
                    if self.chatDataList[chatDataList.count-1].user_id == self.chatDataList[chatDataList.count-2].user_id {
                        self.chatList[chatList.count-1] = ChatItem(chat: self.chatDataList[chatDataList.count-2],
                                                                   nicknameVisible: self.chatList[chatList.count-1].nicknameVisible,
                                                                   timeVisible: false)
                        self.chatList.append(ChatItem(
                                                chat: self.chatDataList[chatDataList.count-1],
                                                nicknameVisible: false,
                                                timeVisible: true))
                    }
                }
            })
            
            AlamofireService.shared.getMembers(roomId: self.room.room_id){ members in
                self.memberList = members
            }
            
            if self.room.category == "데이팅" {
                if UserInfo.shared.ID == self.room.maker {
                    self.title = String(self.room.room_title.split(separator: "&")[1])
                }
                else {
                    self.title = String(self.room.room_title.split(separator: "&")[0])
                }
            }
            else{
                self.title = self.room.room_title
            }
        }
    }
    
    func sendChat() {
        if self.content == "" {
            return
        }
        
        let now = Date()
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
        let date=dateFormatter.string(from: now)
        
        var unreadMembers = ""
        
        self.memberList.forEach { (member) in
            unreadMembers.append("\(member.user_id)|")
        }
        unreadMembers = unreadMembers.replacingOccurrences(of: "\(UserInfo.shared.ID)|", with: "")
        
        
        if chatList.count == 0 || (chatList.last!.chat.chat_time.split(separator: " ")[0]) < date.split(separator: " ")[0] {
            let cal = Calendar(identifier: .gregorian)
            let comps = cal.dateComponents([.weekday], from: now)
            
            var dayOfWeek = ""
            
            switch comps.weekday {
            case 1:
                dayOfWeek = "일요일"
                break
            case 2:
                dayOfWeek = "월요일"
                break
            case 3:
                dayOfWeek = "화요일"
                break
            case 4:
                dayOfWeek = "수요일"
                break
            case 5:
                dayOfWeek = "목요일"
                break
            case 6:
                dayOfWeek = "금요일"
                break
            case 7:
                dayOfWeek = "토요일"
                break
            default:
                print("?")
            }
            
            var systemChat = ChatData(chat_id: "SYSTEM_\(self.room.room_id)_\(date)", room_id: self.room.room_id, user_id: "SYSTEM", user_nickname: "SYSTEM", chat_content: "\(date.split(separator: " ")[0]) \(dayOfWeek)", chat_time: date, unread_member: "", system_chat: 1)
            
            AlamofireService.shared.insertChat(chat: systemChat){ result in
                if result.result == "success" {
                    var chatId = "\(UserInfo.shared.ID)_\(self.room.room_id)_\(date)"
                    var chat = ChatData(chat_id: chatId, room_id: self.room.room_id, user_id: UserInfo.shared.ID, user_nickname: UserInfo.shared.NICKNAME, chat_content: self.content, chat_time: "\(date)_1", unread_member: unreadMembers, system_chat: 0)
                    writeFirebase(chat: systemChat)
                    
                    AlamofireService.shared.insertChat(chat: chat){ result in
                        if result.result == "success" {
                            var topic = room.room_id
                            var title = room.room_title
                            var content = "\(UserInfo.shared.ID) \(self.content)"
                            
                            //AlamofireService.shared.sendFcm(topic: topic, title: title, content: content)
                            writeFirebase(chat: chat)
                            self.content = ""
                        }
                    }
                }
            }
        }
        else {
            var chatId = "\(UserInfo.shared.ID)_\(self.room.room_id)_\(date)"
            var chat = ChatData(chat_id: chatId, room_id: self.room.room_id, user_id: UserInfo.shared.ID, user_nickname: UserInfo.shared.NICKNAME, chat_content: self.content, chat_time: "\(date)_1", unread_member: unreadMembers, system_chat: 0)
            
            AlamofireService.shared.insertChat(chat: chat){ result in
                if result.result == "success" {
                    var topic = room.room_id
                    var title = room.room_title
                    var content = "\(UserInfo.shared.ID) \(self.content)"
                    
                    //AlamofireService.shared.sendFcm(topic: topic, title: title, content: content)
                    writeFirebase(chat: chat)
                    self.content = ""
                }
            }
        }
    }
    
    func writeFirebase(chat: ChatData){
        
        self.ref!.childByAutoId().setValue([
            "chat_id" : chat.chat_id,
            "room_id" : chat.room_id,
            "user_id" : chat.user_id,
            "user_nickname" : chat.user_nickname,
            "chat_content" : chat.chat_content,
            "chat_time" : chat.chat_time,
            "unread_member" : chat.unread_member,
            "system_chat" : chat.system_chat
        ])
    }
}
