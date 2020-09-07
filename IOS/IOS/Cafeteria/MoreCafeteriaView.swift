//
//  MoreCafeteriaView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/03.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct MoreCafeteriaView: View {
    
    @Environment(\.presentationMode) var presentationMode
    
    @State var type : String
    
    var body: some View {
        VStack{
            HStack(spacing: 20){
                Image("black_back_icon")
                    .resizable()
                    .frame(width: 20, height: 20)
                    .onTapGesture {
                        self.presentationMode.wrappedValue.dismiss()
                }
                Text(type)
                    .font(.system(size: 20))
                
                Spacer()
            }
            Spacer()
        }
        .padding()
        .navigationBarTitle("")
        .navigationBarHidden(true)
    }
}

struct MoreCafeteriaView_Previews: PreviewProvider {
    static var previews: some View {
        MoreCafeteriaView(type: "한식")
    }
}
