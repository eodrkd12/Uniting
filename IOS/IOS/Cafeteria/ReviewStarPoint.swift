//
//  ReviewStarPoint.swift
//  IOS
//
//  Created by 김세현 on 2020/09/08.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct ReviewStarPoint: View {
    
    @State var point = 0
    
    @State var reviewSheetVisible = false
    
    @State var editable : Bool
    
    var body: some View {
        HStack(spacing: 10){
            if editable == true {
                Image(systemName: "star.fill")
                    .resizable()
                    .frame(width: 40, height: 40)
                    .foregroundColor(self.point > 0 ? Color.yellow : Colors.grey500)
                    .onTapGesture {
                        self.point = 1
                        self.reviewSheetVisible = true
                }
                Image(systemName: "star.fill")
                    .resizable()
                    .frame(width: 40, height: 40)
                    .foregroundColor(self.point > 1 ? Color.yellow : Colors.grey500)
                    .onTapGesture {
                        self.point = 2
                        self.reviewSheetVisible = true
                }
                Image(systemName: "star.fill")
                    .resizable()
                    .frame(width: 40, height: 40)
                    .foregroundColor(self.point > 2 ? Color.yellow : Colors.grey500)
                    .onTapGesture {
                        self.point = 3
                        self.reviewSheetVisible = true
                }
                Image(systemName: "star.fill")
                    .resizable()
                    .frame(width: 40, height: 40)
                    .foregroundColor(self.point > 3 ? Color.yellow : Colors.grey500)
                    .onTapGesture {
                        self.point = 4
                        self.reviewSheetVisible = true
                }
                Image(systemName: "star.fill")
                    .resizable()
                    .frame(width: 40, height: 40)
                    .foregroundColor(self.point > 4 ? Color.yellow : Colors.grey500)
                    .onTapGesture {
                        self.point = 5
                        self.reviewSheetVisible = true
                }
            }
            else {
                Image(systemName: "star.fill")
                    .resizable()
                    .frame(width: 10, height: 10)
                    .foregroundColor(self.point > 0 ? Color.yellow : Colors.grey500)
                Image(systemName: "star.fill")
                    .resizable()
                    .frame(width: 10, height: 10)
                    .foregroundColor(self.point > 1 ? Color.yellow : Colors.grey500)
                Image(systemName: "star.fill")
                    .resizable()
                    .frame(width: 10, height: 10)
                    .foregroundColor(self.point > 2 ? Color.yellow : Colors.grey500)
                Image(systemName: "star.fill")
                    .resizable()
                    .frame(width: 10, height: 10)
                    .foregroundColor(self.point > 3 ? Color.yellow : Colors.grey500)
                Image(systemName: "star.fill")
                    .resizable()
                    .frame(width: 10, height: 10)
                    .foregroundColor(self.point > 4 ? Color.yellow : Colors.grey500)
            }
        }
        .sheet(isPresented: self.$reviewSheetVisible, content: {
            WriteReviewView(point: self.point)
        })
    }
}
