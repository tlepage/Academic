//
//  BNRMapPoint.h
//  Whereami
//
//  Created by Tom LePage on 9/25/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>
#import <MapKit/MapKit.h>

@interface BNRMapPoint : NSObject
{
    
}

// A new designated initializer for instances of BNRMapPoint
- (id)initWithCoordinate:(CLLocationCoordinate2D)c title:(NSString *)t;

// This is required for MKAnnotation
@property (nonatomic, readonly) CLLocationCoordinate2D coordinate;

// This is optional for MKAnnotation
@property (nonatomic, copy) NSString *title;

@end
