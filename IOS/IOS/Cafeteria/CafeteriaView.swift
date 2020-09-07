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
    
    @ObservedObject var afService = AlamofireService()
    
    @State var cafeteriaListSet : [CafeteriaDataList] = [
        CafeteriaDataList(),
        CafeteriaDataList(),
        CafeteriaDataList(),
        CafeteriaDataList(),
        CafeteriaDataList()
    ]
    
    @State var initCount = 0
    
    var cafeteriaType = ["한식","양식","중식","일식","치킨"]
    
    var body: some View {
        ScrollView{
            if initCount == 5 {
                CafeteriaList(type: "한식", cafeteriaList: cafeteriaListSet[0].items!)
                CafeteriaList(type: "양식", cafeteriaList: cafeteriaListSet[1].items!)
                CafeteriaList(type: "중식", cafeteriaList: cafeteriaListSet[2].items!)
                CafeteriaList(type: "일식", cafeteriaList: cafeteriaListSet[3].items!)
                CafeteriaList(type: "치킨", cafeteriaList: cafeteriaListSet[4].items!)
            }
        }
        .navigationBarTitle("")
        .navigationBarHidden(true)
        .onAppear(){
            
            if self.initCount < 5 {
                self.cafeteriaType.forEach { (type) in
                    self.afService.getCafeteriaList(start: 1,display: 10,query: "성서계명대\(type)",sortingOrder: "reviewCount"){ items in
                        
                        switch type {
                        case "한식":
                            self.cafeteriaListSet[0].items=items
                        case "양식":
                            self.cafeteriaListSet[1].items=items
                        case "중식":
                            self.cafeteriaListSet[2].items=items
                        case "일식":
                            self.cafeteriaListSet[3].items=items
                        case "치킨":
                            self.cafeteriaListSet[4].items=items
                        default: break
                        }
                        
                        self.initCount += 1
                    }
                }
            }
        }
    }
}
