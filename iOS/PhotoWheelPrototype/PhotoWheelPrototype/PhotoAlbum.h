//
//  PhotoAlbum.h
//  PhotoWheelPrototype
//
//  Created by Tom LePage on 9/19/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "_PhotoAlbum.h"

@interface PhotoAlbum : _PhotoAlbum

+ (PhotoAlbum *)newPhotoAlbumWithName:(NSString *)albumName inContext:(NSManagedObjectContext *)context;
+ (NSMutableOrderedSet *)allPhotoAlbumsInContext:(NSManagedObjectContext *)context;

@end
