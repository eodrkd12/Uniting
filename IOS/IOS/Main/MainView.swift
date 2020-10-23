//
//  MainView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/01.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct MainView: View {
    
    @State var selection = 0
    
    var body: some View {
        NavigationView{
            TabView(selection: $selection){
                HomeView().tabItem({
                    Image(selection == 0 ? "home_click_icon" : "home_icon")
                    Text("홈")
                }).tag(0)
                
                MyRoomListView().tabItem({
                    Image(selection == 1 ? "chat_click_icon" : "chat_icon")
                    Text("채팅")
                }).tag(1)
                
                CafeteriaView().tabItem({
                    Image(selection == 2 ? "locate_click_icon" : "locate_icon")
                    Text("맛집")
                }).tag(2)
                
                OptionView().tabItem({
                    Image(selection == 3 ? "option_click_icon" : "option_icon")
                    Text("옵션")
                }).tag(3)
            }
        }
        .navigationBarHidden(true)
    }
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
