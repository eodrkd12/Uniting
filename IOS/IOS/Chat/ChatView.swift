//
//  ChatView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/17.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import FirebaseDatabase

struct ChatView: View {
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @State var room: MyRoomData
    
    @State var ref : DatabaseReference?
    @State var query : DatabaseQuery?
    
    @State var chatList : [ChatItem] = []
    
    var body: some View {
        VStack{
            HStack{
                Image("black_back_icon")
                    .resizable()
                    .frame(width: 20, height: 20)
                    .onTapGesture {
                        self.presentationMode.wrappedValue.dismiss()
                }
                Spacer()
                Text(room.room_title)
                    .font(.system(size: 20))
                    .foregroundColor(Colors.grey700)
                Spacer()
                Image("option_icon")
                    .resizable()
                    .frame(width: 20, height: 20)
            }
            ScrollView{
                EmptyView()
            }
            Spacer()
        }
        .padding()
        .navigationBarTitle("")
        .navigationBarHidden(true)
        .onAppear(){
            self.ref = Database.database().reference().child("chat").child(self.room.room_id)
            self.query = self.ref!.queryOrderedByKey()
            
            self.query?.observe(.childAdded, with: {(snapshot) in
                var key = snapshot.key
                var value = snapshot.value as! [String : Any?]
                
            })
        }
    }
}
