//
//  EditIntroduceView.swift
//  IOS
//
//  Created by 김세현 on 2020/10/28.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct EditIntroduceView: View {
    
    @Environment(\.presentationMode) var presentationMode
    
    @Binding var data: String
    @State var content = ""
    @State var hint = "당신에 대해 이야기 해주세요.(10자 이상)\n매력적인 소개글은 매칭률이 올라갑니다.\n\n카카오톡 아이디 또는 메신저 등 개인 연락처를 노출할 경우에는 소개글이 비노출 됩니다."
    
    var body: some View {
        VStack{
            Text("소개글 작성")
                .font(.system(size: 22))
                .padding()
            Line(width: 2)
            ZStack{
                MultilineTextField(text: hint, content: $content)
            }
            .padding()
            
            Spacer()
            Button(action: {
                data = content
                presentationMode.wrappedValue.dismiss()
            }, label: {
                Text("완료")
                    .frame(width: UIScreen.main.bounds.width)
                    .font(.system(size: 22))
                    .foregroundColor(Color.white)
                    .padding()
                    .padding(.bottom, 10)
                    .background(Colors.primary)
            })
        }
        .padding(.top,5)
        .ignoresSafeArea(.all)
    }
}
