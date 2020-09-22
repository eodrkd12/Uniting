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
    
    @State var room: MyRoomData
    
    @State var ref : DatabaseReference?
    @State var query : DatabaseQuery?
    
    @State var chatList : [ChatItem] = []
    
    @State var menuVisible = false
    
    @State var height : CGFloat=0
    @State var content = ""
    var body: some View {
        ZStack{
            VStack{
                HStack{
                    Image("black_back_icon")
                        .resizable()
                        .frame(width: 20, height: 20)
                        .onTapGesture {
                            self.presentationMode.wrappedValue.dismiss()
                    }
                    Spacer()
                    Text(room.room_title)
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
                List{
                    ForEach(chatList, id:\.chat.chat_id){ chat in
                        chat
                    }
                }
                .onAppear(){
                    UITableView.appearance().separatorStyle = .none
                }
                Spacer()
                HStack{
                    TextField("", text: $content)
                        .lineLimit(4)
                        .font(.system(size: 15))
                    Button(action: {
                        self.sendChat()
                    }){
                        Text("send")
                    }
                }
                .frame(height : UIScreen.main.bounds.height*0.05)
                .padding(.horizontal,15)
                .offset(y: -self.height)
            }
            .padding()
            GeometryReader{ _ in
                HStack{
                    Spacer()
                    RoomMenu(showing: self.presentationMode, room: self.room)
                        .offset(x: self.menuVisible ? 0 : UIScreen.main.bounds.width)
                        .animation(.easeIn(duration: 0.2))
                }
            }.background(Color.black.opacity(self.menuVisible ? 0.3 : 0))
                .padding(.top,50)
        }
        .navigationBarTitle("")
        .navigationBarHidden(true)
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
            self.query = self.ref!.queryOrderedByKey()
            
            self.query?.observe(.childAdded, with: {(snapshot) in
                var key = snapshot.key
                var value = snapshot.value as! [String : Any?]
                
                self.chatList.append(ChatItem(
                    chat: ChatData(
                        chat_id: value["chat_id"].unsafelyUnwrapped as! String,
                        room_id: value["room_id"].unsafelyUnwrapped as! String,
                        user_id: value["user_id"].unsafelyUnwrapped as! String,
                        user_nickname: value["user_nickname"].unsafelyUnwrapped as! String,
                        chat_content: value["chat_content"].unsafelyUnwrapped as! String,
                        chat_time: value["chat_time"].unsafelyUnwrapped as! String,
                        unread_member: value["unread_member"].unsafelyUnwrapped as! String,
                        system_chat: value["system_chat"].unsafelyUnwrapped as! Int
                    )
                ))
            })
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
        /*
        var chatPartner=""
        if UserInfo.shared.ID == self.room!.room_maker {
            chatPartner=self.room!.room_partner
        }
        else {
            chatPartner=self.room!.room_maker
        }
        
        HttpService.shared.insertChatReq(roomId: self.room!.room_id, userId: UserInfo.shared.ID, userNickname: UserInfo.shared.NICKNAME, chatPartner: chatPartner, chatContent: self.content, currentDate: date){ resultModel -> Void in
            
            self.ref!.childByAutoId().setValue([
                "room_id" : self.room!.room_id,
                "chat_speaker" : UserInfo.shared.ID,
                "chat_speaker_nickname" : UserInfo.shared.NICKNAME,
                "chat_content" : self.content,
                "chat_time" : date,
                "unread_count" : 1
            ])
            self.content=""
        }
         */
    }
}
