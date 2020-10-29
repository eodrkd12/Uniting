//
//  Menu.swift
//  IOS
//
//  Created by 김세현 on 2020/09/18.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct RoomMenu : View {
    
    @Binding var presentationMode : PresentationMode
    @State var room: RoomData
    @Binding var memberList: [MemberData]
    
    var body: some View {
        
        VStack(spacing: 10) {
            HStack{
                Text("대화상대")
                    .padding(.leading,10)
                    .padding(.top,5)
                    .font(.system(size: 20))
                    .foregroundColor(Colors.grey500)
                Spacer()
            }
            ScrollView{
                RoomMenuMemberRow(member: MemberData(user_id: UserInfo.shared.ID, user_nickname: UserInfo.shared.NICKNAME))
                ForEach(memberList, id:\.user_id){ member in
                    if member.user_id != UserInfo.shared.ID{
                        RoomMenuMemberRow(member: member)
                    }
                }
            }
            Spacer()
            HStack{
                Button(action: {
                    if memberList.count == 1 {
                        AlamofireService.shared.exitRoom(roomId: room.room_id, userId: UserInfo.shared.ID, type: "delete"){ result in
                            self.presentationMode.dismiss()
                        }
                    }
                    else {
                        AlamofireService.shared.exitRoom(roomId: room.room_id, userId: UserInfo.shared.ID, type: "exit"){ result in
                            self.presentationMode.dismiss()
                        }
                    }
                }, label: {
                    Image("exit_icon")
                        .resizable()
                        .frame(width:30, height: 30)
                })
                .padding(.top,10)
                .padding()
                
                Spacer()
                
                Button(action: {
                    
                }, label: {
                    Text("알림")
                        .foregroundColor(Color.black)
                })
                .padding(.top,10)
                .padding()
            }
            .padding(.bottom,33)
            .frame(height: 70)
            .background(Colors.grey300)
        }
        .frame(width: UIScreen.main.bounds.width*0.6)
        .background(Color.white)
        .edgesIgnoringSafeArea(.bottom)
    }
}

struct RoomMenuMemberRow : View {
    
    @State var member: MemberData
    
    var body: some View{
        VStack{
            HStack{
                Text(member.user_nickname)
                    .font(.system(size: 20))
                Spacer()
            }
            .padding(.vertical,4)
            .padding(.horizontal,20)
        }
    }
}
