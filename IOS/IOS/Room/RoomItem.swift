//
//  RoomITem.swift
//  IOS
//
//  Created by 김세현 on 2020/09/25.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct RoomItem: View {
    
    @State var room : RoomData
    @State var numMember = 0
    
    var body: some View {
        HStack{
            Image("face-black-18dp")
                .resizable()
                .frame(width: 70, height: 70)
            VStack(spacing: 5){
                HStack{
                    Text(room.room_title)
                        .font(.system(size: 20))
                        .foregroundColor(Colors.grey700)
                    Text("\(numMember)명 참가중")
                        .font(.system(size: 15))
                        .foregroundColor(Colors.grey500)
                    Spacer()
                }
                HStack{
                    Text(room.room_introduce)
                        .font(.system(size: 15))
                        .foregroundColor(Colors.grey500)
                    Spacer()
                }
            }
            .frame(width: UIScreen.main.bounds.width * 0.6)
            NavigationLink(destination: ChatView(room: room)){
                Image("openchat_button")
                    .resizable()
                    .frame(width: 45, height: 25)
            }
        }
        .padding()
        .onAppear(){
            AlamofireService.shared.getMembers(roomId: self.room.room_id){ memberList in
                self.numMember = memberList.count
            }
        }
    }
}
