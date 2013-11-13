//
//  _Photo.h
//  PhotoWheelPrototype
//
//  Created by Tom LePage on 9/19/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class _PhotoAlbum;

@interface _Photo : NSManagedObject

@property (nonatomic, retain) NSDate * dateAdded;
@property (nonatomic, retain) NSData * originalImageData;
@property (nonatomic, retain) NSData * thumbnailImageData;
@property (nonatomic, retain) NSData * largeImageData;
@property (nonatomic, retain) _PhotoAlbum *photoAlbum;

@end
