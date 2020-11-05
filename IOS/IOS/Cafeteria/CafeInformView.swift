//
//  DetailView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/03.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import NMapsMap
import SwiftSoup

struct CafeInformView: View {
    @Environment(\.presentationMode) var presentationMode
    @State var cafeNo : Int
    @State var cafeteria = Cafeteria(inform: CafeteriaInform(cafe_address: "", cafe_name: "", cafe_phone: "", cafe_bizhour: "", cafe_mapx: 0, cafe_mapy: 0),
                                     menu: [],
                                     review: [])
    @State var cafeInit = false
    @State var mapView = NMFMapView(frame: CGRect(x: 0, y: 0, width: 200, height: 200))
    @State var showMoreTimes = false
    @State var moreTimes : [String.SubSequence] = []
    @State var firstTime : String.SubSequence = ""
    
    @State var rows:Int = 0
    @State var total:Int = 0
    
    var body: some View {
        ZStack{
            ScrollView{
                VStack(spacing: 20){
                    HStack{
                        Spacer()
                        if cafeInit {
                            MapView(cgRect: CGRect(x: 0, y: 0, width: UIScreen.main.bounds.width*0.95, height: 250), x: cafeteria.inform.cafe_mapx, y: cafeteria.inform.cafe_mapy)
                                .frame(width: UIScreen.main.bounds.width*0.95, height: 250)
                        }
                        Spacer()
                    }
                    .frame(height: 250)
                    .padding(.top,65)
                    VStack(spacing: 10){
                        HStack{
                            Text(cafeteria.inform.cafe_name)
                                .font(.system(size: 30))
                                .foregroundColor(Colors.grey900)
                            Spacer()
                        }
                        HStack(spacing: 10){
                            Image("call_icon")
                                .resizable()
                                .frame(width: 20, height: 20)
                            
                            if cafeteria.inform.cafe_phone == "" {
                                Text("등록된 정보가 없습니다.")
                            }
                            else {
                                Button(action: {
                                    var tel = "tel://\(cafeteria.inform.cafe_phone)"
                                    guard let url = URL(string: tel) else { return }
                                    if #available(iOS 10, *) {
                                        UIApplication.shared.open(url)
                                    }
                                    else {
                                        UIApplication.shared.openURL(url)
                                    }
                                }, label: {
                                    Text(cafeteria.inform.cafe_phone)
                                })
                            }
                            
                            Spacer()
                        }
                        HStack(spacing: 10){
                            Image("marker_icon")
                                .resizable()
                                .frame(width: 20, height: 20)
                            Text(cafeteria.inform.cafe_address)
                            Spacer()
                        }
                        HStack(alignment: .top, spacing: 10){
                            Image("time_icon")
                                .resizable()
                                .frame(width: 20, height: 20)
                            
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
                    .padding(.leading, 10)
                }
                .padding(.vertical,15)
                Line(width: 10)
                VStack(spacing: 20){
                    HStack{
                        Text("메뉴")
                            .font(.system(size: 25))
                            .foregroundColor(Colors.grey900)
                        Spacer()
                        if cafeteria.review.count > 5 {
                            Button {
                                
                            } label: {
                                Text("더보기")
                                    .font(.system(size: 20))
                                    .foregroundColor(Colors.grey500)
                            }
                        }
                    }
                    .padding(.leading, 10)
                    VStack(spacing: 10){
                        if cafeteria.menu.count == 0 {
                            Text("등록된 메뉴가 없습니다.")
                                .font(.system(size: 20))
                                .foregroundColor(Colors.grey500)
                        }
                        else {
                            ForEach(cafeteria.menu.count > 5 ? 0..<5 : 0..<cafeteria.menu.count, id: \.self){ i in
                                MenuRow(menu: cafeteria.menu[i])
                            }
                        }
                    }
                    .padding(.horizontal, 15)
                }
                .padding(.vertical,15)
                Line(width: 10)
                VStack(spacing: 20){
                    Text("평가를 남겨주세요.")
                        .font(.system(size: 25))
                    
                    ReviewStarPoint(editable: true)
                    
                }
                .padding(.vertical,15)
                Line(width: 10)
                VStack(spacing: 20){
                    HStack{
                        Text("리뷰")
                            .font(.system(size: 25))
                            .foregroundColor(Colors.grey900)
                        Spacer()
                        if cafeteria.review.count > 3 {
                            Button {
                                
                            } label: {
                                Text("더보기")
                                    .font(.system(size: 20))
                                    .foregroundColor(Colors.grey500)
                            }
                        }
                    }
                    .padding(.horizontal, 10)
                    VStack(spacing: 10){
                        if cafeteria.review.count == 0 {
                            Text("등록된 리뷰가 없습니다.")
                                .font(.system(size: 20))
                                .foregroundColor(Colors.grey500)
                        }
                        else {
                            ForEach(cafeteria.review.count > 3 ? 0..<3 : 0..<cafeteria.review.count, id: \.self){ i in
                                ReviewItem(review: cafeteria.review[i])
                            }
                        }
                    }
                    .padding(.horizontal, 15)
                }
                .padding(.vertical,15)
                Spacer()
            }
            VStack{
                Button(action: {
                    presentationMode.wrappedValue.dismiss()
                }, label: {
                    HStack{
                        Image("black_back_icon")
                            .resizable()
                            .frame(width:20, height: 20)
                            .padding()
                        Spacer()
                    }
                })
                Spacer()
            }
            .padding(.top,30)
        }
        .edgesIgnoringSafeArea(.top)
        .navigationBarTitle("")
        .navigationBarHidden(true)
        .onAppear(){
            AlamofireService.shared.getCafeteriaInform(cafeNo: self.cafeNo){ cafeteria in
                self.cafeteria = cafeteria
                self.cafeInit = true
            }
        }
    }
}
