//
//  WriteReviewView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/08.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct WriteReviewView: View {
    
    @Environment(\.presentationMode) var presentationMode
    
    @State var point : Int
    
    @State var content=""
    
    @State var image : Image?
    
    @State var imagePickerVisiable=false
    @State var inputImage : UIImage?
    @State var imageUrl : URL?
    
    @State var value : CGFloat=0
    
    var body: some View {
        VStack(spacing: 10){
            HStack{
                Spacer()
                Button(action: {
                    
                }) {
                    Text("등록")
                        .font(.system(size: 20))
                }
            }
            
            ReviewStarPoint(point: point, editable: true)
            
            MultilineTextField(text: "식당해 대해 리뷰를 남겨주세요.\n적절하지 않은 내용의 글일 경우 비노출 될 수 있습니다.", content: self.$content)
            
            HStack{
                Spacer()
                if image != nil{
                    image?
                        .resizable()
                        .scaledToFit()
                }
                Spacer()
            }
            .frame(height: 200)
            
            Button(action: {
                self.imagePickerVisiable=true
            }){
                Text("사진 추가")
            }
            .offset(y: -self.value)
            .onAppear(){
                NotificationCenter.default.addObserver(forName: UIResponder.keyboardWillShowNotification, object: nil, queue: .main) { noti in
                    let value = noti.userInfo![UIResponder.keyboardFrameEndUserInfoKey] as! CGRect
                    let height = value.height
                    
                    self.value=height
                }
                
                NotificationCenter.default.addObserver(forName: UIResponder.keyboardWillHideNotification, object: nil, queue: .main){ noti in
                    self.value=0
                }
            }
        }
        .padding()
        .sheet(isPresented: $imagePickerVisiable, onDismiss: loadImage){
            ImagePicker(image: self.$inputImage, imageUrl: self.$imageUrl)
        }
        .onTapGesture {
            UIApplication.shared.endEditing()
        }
    }
    
    func loadImage(){
        guard let inputImage=inputImage else {
            return
        }
        image=Image(uiImage: inputImage)
    }
    
    func writeReview(){
        if inputImage != nil{
            let now = Date()
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
            let date=dateFormatter.string(from: now)
            
            
            presentationMode.wrappedValue.dismiss()
        }
    }
}
