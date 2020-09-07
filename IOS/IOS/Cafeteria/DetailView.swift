//
//  DetailView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/03.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import NMapsMap

struct DetailView: View {
    @Environment(\.presentationMode) var presentationMode
    
    @State var mapView = NMFMapView(frame: CGRect(x: 0, y: 0, width: 200, height: 200))
    @State var cafeteria : CafeteriaData
    @State var showMoreTimes = false
    @State var moreTimes : [String.SubSequence] = []
    @State var firstTime : String.SubSequence = ""
    
    
    var body: some View {
        ScrollView{
            VStack(spacing: 10){
                HStack{
                    Spacer()
                    MapView(cgRect: CGRect(x: 0, y: 0, width: UIScreen.main.bounds.width*0.95, height: 250), x: cafeteria.x, y: cafeteria.y)
                        .frame(width: UIScreen.main.bounds.width*0.95, height: 250)
                        .padding(.top,65)
                    Spacer()
                }
                HStack{
                    Text(cafeteria.name)
                        .font(.system(size: 30))
                        .foregroundColor(Colors.grey900)
                        .padding(.leading,10)
                    Spacer()
                }
                HStack(spacing: 10){
                    Image("call_icon")
                        .resizable()
                        .frame(width: 20, height: 20)
                        .padding(.leading, 10)
                    Text(cafeteria.phone)
                    Spacer()
                }
                HStack(spacing: 10){
                    Image("marker_icon")
                        .resizable()
                        .frame(width: 20, height: 20)
                        .padding(.leading, 10)
                    Text(cafeteria.roadAddr)
                    Spacer()
                }
                HStack(alignment: .top, spacing: 10){
                    Image("time_icon")
                        .resizable()
                        .frame(width: 20, height: 20)
                        .padding(.leading, 10)
                    
                    if moreTimes.count == 0 {
                        
                        Text("등록된 정보가 없습니다.")
                    }
                    else {
                        HStack(alignment: .top, spacing: 5){
                            VStack{
                                Text(firstTime)
                                if showMoreTimes {
                                    ForEach(moreTimes, id: \.self){ time in
                                        Text(time)
                                    }
                                }
                            }
                            if moreTimes.count > 1 {
                                Image("more_icon")
                                    .resizable()
                                    .frame(width: 15, height: 15)
                                    .onTapGesture {
                                        self.showMoreTimes.toggle()
                                }
                                .padding(.top,3)
                            }
                        }
                    }
                    Spacer()
                }
            }
            Spacer()
        }
        .edgesIgnoringSafeArea(.top)
        .navigationBarTitle("")
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(
            leading: Image("black_back_icon")
                .onTapGesture {
                    self.presentationMode.wrappedValue.dismiss()
            }
        )
            .onAppear(){
                let bizHourInfo = self.cafeteria.bizHourInfo.replacingOccurrences(of: " ", with: "")
                self.moreTimes = bizHourInfo.split(separator: "|")
                self.firstTime = self.moreTimes[0]
                self.moreTimes.remove(at: 0)
        }
    }
}
