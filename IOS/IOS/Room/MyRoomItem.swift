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
    
    @State var lastChat = ""
    @State var lastChatTime = ""
    
    @State var title = ""
    
    var body: some View{
        HStack(spacing: 5){
            NavigationLink(destination: ChatView(room: room)){
                Image("face-black-18dp")
                    .resizable()
                    .frame(width: 70, height: 70)
                VStack(spacing: 5){
                    HStack{
                        Text(title)
                            .font(.system(size: 20))
                            .foregroundColor(Colors.grey700)
                            .truncationMode(.tail)
                            .lineLimit(1)
                        Spacer()
                    }
                    .frame(width:150)
                    HStack{
                        Text(lastChat)
                            .font(.system(size: 18))
                            .foregroundColor(Colors.grey500)
                            .truncationMode(.tail)
                            .lineLimit(2)
                        Spacer()
                    }
                    .frame(width:150)
                    Spacer()
                }
                Spacer()
                VStack{
                    Text(lastChatTime)
                        .font(.system(size: 18))
                        .foregroundColor(Colors.grey500)
                    Spacer()
                }
            }
        }
        .padding()
        .onAppear(){
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
}
