//
//  OpenChatView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/01.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct OpenChatView: View {
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @State var index = 0
    @State var categoryList = ["취미","스터디","고민상담","잡담"]
    
    var body: some View {
        VStack(spacing: 20){
            PagingView(index: self.$index.animation(), maxIndex: 3){
                PageView(image: Image("test"), title: "취미", subTitle: "사람들과 취미를 공유해보세요")
                PageView(image: Image("test"), title: "스터디", subTitle: "사람들과 스터디를 해보세요")
                PageView(image: Image("test"), title: "고민상담", subTitle: "사람들에게 고민상담을 해보세요")
                PageView(image: Image("test"), title: "잡담", subTitle: "사람들과 이야기를 나눠보세요")
            }
            .aspectRatio(4/4, contentMode: .fit)
            .clipShape(RoundedRectangle(cornerRadius: 10))
            .frame(width: UIScreen.main.bounds.width, height: 280)
            
            HStack(spacing: 15){
                NavigationLink(destination: RoomListView(title: categoryList[index]), label:{
                    Image("main_connect_button")
                } )
                .foregroundColor(Color.clear)
                NavigationLink(destination: MakeRoomView(), label:{
                    Image("openchat_make_icon")
                } )
                .foregroundColor(Color.clear)
            }
            Spacer()
        }
    }
}

struct OpenChatView_Previews: PreviewProvider {
    static var previews: some View {
        OpenChatView()
    }
}
