//
//  CafeteriaView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/01.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import SwiftSoup

struct CafeteriaView: View {
    
    @State var cafeteriaList : CafeteriaList? = nil
    
    var body: some View {
        ScrollView{
            if cafeteriaList != nil {
                PreviewList(type: "한식", previewList: cafeteriaList!.koreanFood)
                PreviewList(type: "중식", previewList: cafeteriaList!.chineseFood)
                PreviewList(type: "양식", previewList: cafeteriaList!.westernFood)
                PreviewList(type: "일식", previewList: cafeteriaList!.japaneseFood)
                PreviewList(type: "패스트푸드", previewList: cafeteriaList!.fastFood)
            }
        }
        .navigationBarTitle("")
        .navigationBarHidden(true)
        .onAppear(){
            AlamofireService.shared.getCafeteriaList(){ cafeteriaList in
                self.cafeteriaList = cafeteriaList
            }
        }
    }
}

