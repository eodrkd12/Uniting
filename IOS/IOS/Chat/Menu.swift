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
    var room: MyRoomData
    
    var body: some View {
        
        VStack(spacing: 10) {
            HStack{
                Text("대화상대")
                    .padding()
                    .font(.system(size: 20))
                    .foregroundColor(Colors.grey500)
                Spacer()
            }
            
            Spacer()
            HStack{
                Text("나가기")
                    .padding()
                Spacer()
            }
            .frame(height: 50)
            .background(Colors.grey300)
        }
        .frame(width: UIScreen.main.bounds.width*0.6)
        .background(Color.white)
        .edgesIgnoringSafeArea(.bottom)
    }
}
