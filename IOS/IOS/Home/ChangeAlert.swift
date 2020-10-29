//
//  ChangeAlert.swift
//  IOS
//
//  Created by 김세현 on 2020/09/01.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct ChangeAlert: View {
    
    @Binding var showing : Bool
    @Binding var index : Int
    
    var body: some View {
        VStack(spacing: 10){
            Text("매칭 조건 사용불가")
                .font(.system(size: 20))
                .foregroundColor(Colors.grey700)
            
            Text("스마트매칭에서 사용할 수 있습니다.")
                .font(.system(size:15))
                .foregroundColor(Colors.grey500)
            Text("매칭모드를 바꾸시겠습니까?")
                .font(.system(size:15))
                .foregroundColor(Colors.grey500)
            
            HStack(spacing: 10){
                Image("dialog_cancel_button")
                    .resizable()
                    .frame(width: 100, height: 40)
                    .onTapGesture {
                        self.showing = false
                }
                Image("dialog_change_button")
                    .resizable()
                    .frame(width: 100, height: 40)
                    .onTapGesture {
                        self.index=1
                        self.showing = false
                }
            }
        }
        .padding(30)
        .background(Color.white)
    }
}
