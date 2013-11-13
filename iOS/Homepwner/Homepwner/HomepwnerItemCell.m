//
//  HomepwnerItemCell.m
//  Homepwner
//
//  Created by Tom LePage on 10/2/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "HomepwnerItemCell.h"

@implementation HomepwnerItemCell

@synthesize controller, tableView;

- (IBAction)showImage:(id)sender
{
    // Get this name of this method, "showImage:"
    NSString *selector = NSStringFromSelector(_cmd);
    
    // selector is now "showImage:atIndexPath:"
    selector = [selector stringByAppendingString:@"atIndexPath:"];
    
    // Prepare a selector from this string
    SEL newSelector = NSSelectorFromString(selector);
    
    NSIndexPath *indexPath = [[self tableView] indexPathForCell:self];

    if (indexPath)
    {
        if ([[self controller] respondsToSelector:newSelector])
        {
            [[self controller] performSelector:newSelector withObject:sender withObject:indexPath];
        }
    }
}

@end
