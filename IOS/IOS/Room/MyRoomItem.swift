//
//  MyRoomItem.swift
//  IOS
//
//  Created by 김세현 on 2020/09/17.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import FirebaseDatabase

struct MyRoomItem : View {
    
    @State var room : RoomData
    
    @State var ref : DatabaseReference?
    @State var query : DatabaseQuery?
    
    @State var lastChat = ""
    @State var lastChatTime = ""
    
    
    var body: some View{
        HStack(spacing: 5){
            NavigationLink(destination: ChatView(room: room)){
                Image("face-black-18dp")
                    .resizable()
                    .frame(width: 70, height: 70)
                VStack(spacing: 5){
                    Text(room.room_title)
                        .font(.system(size: 20))
                        .foregroundColor(Colors.grey700)
                    Text(lastChat)
                        .font(.system(size: 15))
                        .foregroundColor(Colors.grey500)
                    Spacer()
                }
                Spacer()
                VStack{
                    Text(lastChatTime)
                        .font(.system(size: 15))
                        .foregroundColor(Colors.grey500)
                    Spacer()
                }
            }
        }
        .padding()
        .onAppear(){
            self.ref = Database.database().reference().child("chat").child(self.room.room_id)
            self.query = self.ref!.queryOrderedByKey().queryLimited(toLast: 1)
            
            self.query?.observe(.childAdded, with: { (snapshot) in
                var value = snapshot.value as! [String : Any?]
                
                self.lastChat = value["chat_content"].unsafelyUnwrapped as! String
                
                var time = (value["chat_time"].unsafelyUnwrapped as! String).split(separator: " ")[1]
                var hour = Int(time.split(separator: ":")[0])
                var timeStr = ""
                
                if hour! >= 12 {
                    timeStr = "오후 \(hour!-12):\(Int(time.split(separator: ":")[1]).unsafelyUnwrapped)"
                }
                else {
                    timeStr = "오전 \(hour!):\(Int(time.split(separator: ":")[1]).unsafelyUnwrapped)"
                }
                
                self.lastChatTime = timeStr
            })
        }
    }
}
