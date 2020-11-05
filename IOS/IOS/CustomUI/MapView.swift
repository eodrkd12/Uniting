//
//  MapView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/03.
//  Copyright © 2020 KSH. All rights reserved.
//
import SwiftUI
import NMapsMap

struct MapView: UIViewRepresentable {
    
    @State var cgRect : CGRect
    @State var x : Double
    @State var y : Double
    
    func makeUIView(context: UIViewRepresentableContext<MapView>) -> UIView {
        
        let view = UIView()

        var nmgLatlng = NMGLatLng(lat: y, lng: x)
        
        let mapView = NMFMapView(frame: cgRect)
        mapView.mapType = .basic
        mapView.setLayerGroup("NMF_LAYER_GROUP_BUILDING", isEnabled: true)
        mapView.setLayerGroup("NMF_LAYER_GROUP_TRANSIT", isEnabled: true)
        mapView.latitude = nmgLatlng.lat
        mapView.longitude = nmgLatlng.lng
        mapView.zoomLevel = 17
        
        let marker = NMFMarker()
        marker.position = nmgLatlng
        marker.mapView = mapView
        
        
        view.addSubview(mapView)
        
        return view
    }
    
    func updateUIView(_ uiView: UIView, context: UIViewRepresentableContext<MapView>) {
        
    }
}
