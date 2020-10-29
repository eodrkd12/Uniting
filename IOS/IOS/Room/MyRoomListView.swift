//
//  MyRoomListView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/01.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct MyRoomListView: View {
    
    @State var roomList : [MyRoomItem] = []
    
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
                    self.roomList.append(MyRoomItem(room: myRoom))
                }
            }
        }
        .navigationBarTitle("")
        .navigationBarHidden(true)
    }
}
