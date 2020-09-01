//
//  MainView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/01.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct MainView: View {
    var body: some View {
        TabView{
            HomeView().tabItem({
                Text("홈")
                }).tag(0)
            
            MyRoomListView().tabItem({
                Text("채팅")
                }).tag(1)
            
            CafeteriaView().tabItem({
                Text("맛집")
                }).tag(2)
            
            OptionView().tabItem({
                Text("옵션")
                }).tag(3)
        }
    }
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
