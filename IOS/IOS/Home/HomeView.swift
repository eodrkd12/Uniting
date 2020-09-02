//
//  HomeView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/01.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct HomeView: View {
    
    @State var matchingMode : Bool = true
    
    var body: some View {
        VStack{
            if matchingMode == true {
                Image("openchat_button-1")
                    .onTapGesture {
                        self.matchingMode.toggle()
                }
            }
            else {
                Image("by1_button")
                    .onTapGesture {
                        self.matchingMode.toggle()
                }
            }
            
            if matchingMode == true {
                MatchingView()
            }
            else {
                OpenChatView()
            }
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
