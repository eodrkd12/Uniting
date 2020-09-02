//
//  MatchingView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/01.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct MatchingView: View {
    
    @State var index = 0
    @State var changeAlertVisible = false
    
    var body: some View {
        ZStack{
            VStack(spacing: 10){
                PagingView(index: self.$index.animation(), maxIndex: 1){
                    PageView(image: Image("test"), title: "일반 매칭", subTitle: "유니팅에 가입한 상대방과 매칭")
                    PageView(image: Image("test"), title: "스마트 매칭", subTitle: "유니팅에 가입한 상대방 스마트하게 매칭")
                }
                .aspectRatio(4/4, contentMode: .fit)
                .clipShape(RoundedRectangle(cornerRadius: 10))
                .frame(width: UIScreen.main.bounds.width, height: 280)
                
                VStack(spacing: 10){
                    HStack{
                        Text("매칭조건")
                            .font(.system(size: 17))
                            .foregroundColor(Colors.grey700)
                            .padding(.leading,30)
                        Spacer()
                    }
                    EditConditionRow(index: self.$index, changeAlertVisible: self.$changeAlertVisible, title: "키")
                    EditConditionRow(index: self.$index, changeAlertVisible: self.$changeAlertVisible, title: "나이")
                    EditConditionRow(index: self.$index, changeAlertVisible: self.$changeAlertVisible, title: "학과")
                    EditConditionRow(index: self.$index, changeAlertVisible: self.$changeAlertVisible, title: "취미")
                    EditConditionRow(index: self.$index, changeAlertVisible: self.$changeAlertVisible, title: "성격")
                }
                
                Image("big_main_connect_button")
                    .resizable()
                    .frame(width: 270, height: 40)
            }
            
            if changeAlertVisible {
                GeometryReader{_ in
                    ChangeAlert(showing: self.$changeAlertVisible, index: self.$index)
                }.background(Color.black.opacity(0.5).edgesIgnoringSafeArea(.all))
            }
        }
    }
}

struct MatchingView_Previews: PreviewProvider {
    static var previews: some View {
        MatchingView()
    }
}

