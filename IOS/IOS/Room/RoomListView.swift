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
    
    var body: some View {
        VStack{
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
            Spacer()
        }
        .navigationBarTitle("")
        .navigationBarHidden(true)
        .onAppear(){
            
        }
    }
}
