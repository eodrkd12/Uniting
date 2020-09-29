//
//  RoomListView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/24.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct RoomListView: View {
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @State var title : String
    
    @State var roomList : [RoomItem] = []
    
    var body: some View {
        VStack{
            if roomList.count != 0 {
                HStack{
                    HStack{
                        Image("black_back_icon")
                            .resizable()
                            .frame(width: 20, height: 20)
                            .onTapGesture {
                                self.presentationMode.wrappedValue.dismiss()
                            }
                    }
                    .frame(width: UIScreen.main.bounds.width/3, height: 40, alignment: .leading)
                    .padding(.leading,20)
                    HStack{
                        Text(title)
                            .font(.system(size: 20))
                            .foregroundColor(Colors.grey700)
                    }
                    .frame(width: UIScreen.main.bounds.width/3, height: 40)
                    HStack{
                        EmptyView()
                    }
                    .frame(width: UIScreen.main.bounds.width/3, height: 40)
                }
                ScrollView{
                    ForEach(self.roomList, id: \.room.room_id){ room in
                        room
                    }
                }
                Spacer()
            }
            else {
                Text("대화방이 없습니다.")
                    .font(.system(size: 20))
                    .foregroundColor(Colors.grey500)
                Text("지금 개설해 보세요!")
                    .font(.system(size: 20))
                    .foregroundColor(Colors.grey500)
                NavigationLink(destination: MakeRoomView()){
                    Image("openchat_make_icon")
                }
            }
        }
        .navigationBarTitle("")
        .navigationBarHidden(true)
        .onAppear(){
            self.roomList.removeAll()
            AlamofireService.shared.getOpenRoom(univName: UserInfo.shared.UNIV, category: title){ roomDataList in
                roomDataList.forEach{ room in
                    self.roomList.append(
                        RoomItem(room: room)
                    )
                }
            }
        }
    }
}
