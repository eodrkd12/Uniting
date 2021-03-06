//
//  MyRoomListView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/01.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import FirebaseDatabase

struct MyRoomListView: View {
    
    @State var roomList : [MyRoomItem] = []
    
    @State var ref : DatabaseReference?
    @State var query : DatabaseQuery?
    
    var body: some View {
        ScrollView{
            if roomList.count > 0 {
                ForEach(roomList, id: \.room.room_id){ (room) in
                    room
                }
            }
        }
        .onAppear(){
            AlamofireService.shared.getMyRoom(userId: UserInfo.shared.ID){ (myRoomList) in
                self.roomList.removeAll()
                myRoomList.forEach { (myRoom) in
                    self.ref = Database.database().reference().child("chat").child(myRoom.room_id)
                    self.query = self.ref!.queryOrderedByKey().queryLimited(toLast: 1)
                    
                    self.query?.observe(.childAdded, with: { (snapshot) in
                        let value = snapshot.value as! [String : Any?]
                        
                        let lastChat = value["chat_content"].unsafelyUnwrapped as! String
                        
                        let time = (value["chat_time"].unsafelyUnwrapped as! String).split(separator: " ")[1]
                        let hour = Int(time.split(separator: ":")[0])
                        let minute = Int(time.split(separator: ":")[1]).unsafelyUnwrapped
                        var minuteStr = "\(minute)"
                        if minute < 10 {
                            minuteStr = "0\(minute)"
                        }
                        var timeStr = ""
                        if hour! >= 12 {
                            timeStr = "오후 \(hour!-12):\(minuteStr)"
                        }
                        else {
                            timeStr = "오전 \(hour!):\(minuteStr)"
                        }
                        
                        self.roomList.append(MyRoomItem(room: myRoom, lastChat: lastChat, lastChatTime: timeStr))
                        
                        self.roomList.sort { (first, second) -> Bool in
                            
                            return first.lastChatTime > second.lastChatTime
                        }
                    })
                }
            }
        }
        .navigationBarTitle("")
        .navigationBarHidden(true)
    }
}
