//
//  Menu.swift
//  IOS
//
//  Created by 김세현 on 2020/09/18.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct RoomMenu : View {
    
    @Binding var showing : PresentationMode
    @State var room: RoomData
    @Binding var memberList: [MemberData]
    
    var body: some View {
        
        VStack(spacing: 10) {
            HStack{
                Text("대화상대")
                    .padding()
                    .font(.system(size: 20))
                    .foregroundColor(Colors.grey500)
                Spacer()
            }
            ScrollView{
                ForEach(memberList, id:\.user_id){ member in
                    RoomMenuMemberRow(member: member)
                }
            }
            Spacer()
            HStack{
                Text("나가기")
                    .padding()
                    .padding(.bottom,33)
                Spacer()
            }
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
        }
    }
}
