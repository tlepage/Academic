//
//  WhereamiViewController.m
//  Whereami
//
//  Created by Tom LePage on 9/25/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "WhereamiViewController.h"
#import "BNRMapPoint.h"

@interface WhereamiViewController ()

@end

@implementation WhereamiViewController

- (void)findLocation
{
    [locationManager startUpdatingLocation];
    [activityIndicator startAnimating];
    [locationTitleField setHidden:YES];
}

- (void)foundLocation:(CLLocation *)loc
{
    CLLocationCoordinate2D coord = [loc coordinate];
    
    // Create an instance of BNRMapPoint with the current data
    BNRMapPoint *mp = [[BNRMapPoint alloc] initWithCoordinate:coord title:[locationTitleField text]];
    
    // Add it to the map view
    [worldView addAnnotation:mp];
    
    // Zoom the region to this location
    MKCoordinateRegion region = MKCoordinateRegionMakeWithDistance(coord, 250, 250);
    [worldView setRegion:region];
    
    // Reset the UI
    [locationTitleField setText:@""];
    [activityIndicator stopAnimating];
    [locationTitleField setHidden:NO];
    [locationManager stopUpdatingLocation];
}

- (void)viewDidLoad
{
    [worldView setShowsUserLocation:YES];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self)
    {
        // Create location manager object
        locationManager = [[CLLocationManager alloc] init];
        
        // set ourselves as the delegate
        [locationManager setDelegate:self];
        
        // we are setting to max accuracy
        [locationManager setDesiredAccuracy:kCLLocationAccuracyBest];
        
        [locationManager setDistanceFilter:50.0];
        
        // tell our manager to start looking for us immediately
        //[locationManager startUpdatingLocation];
        
        // register the segmented controller
        [mapTypeSegmentedController addTarget:self action:@selector(switchMapType:) forControlEvents:UIControlEventValueChanged];
        [mapTypeSegmentedController setSelectedSegmentIndex:0];
    }
    
    return self;
}

- (IBAction)switchMapType:(id)sender
{
    // Get selected index
    int selectedIndex = [mapTypeSegmentedController selectedSegmentIndex];
    
    switch (selectedIndex)
    {
        // Standard
        case 0:
            [worldView setMapType:MKMapTypeStandard];
            break;
        // Satellite
        case 1:
            [worldView setMapType:MKMapTypeSatellite];
            break;
        // Hybrid
        case 2:
            [worldView setMapType:MKMapTypeHybrid];
            break; 
    }
}

- (void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation
{
    NSLog(@"%@", newLocation);
    
    // How many seconds ago was this new location created?
    NSTimeInterval t = [[newLocation timestamp] timeIntervalSinceNow];
    
    // CLLocationManagers will return the last found location of the device first,
    // you don't want that data in this case.
    // If this location was made more than 3 minutes ago, ignore it.
    if (t < -180)
    {
        // cached data, ignore it
        return;
    }
    
    [self foundLocation:newLocation];
}

- (void)locationManager:(CLLocationManager *)manager didFailWithError:(NSError *)error
{
    NSLog(@"Could not find location: %@", error);
}

- (void)dealloc
{
    // Tell the location manager to stop sending us messages
    // since delegate is __unsafe_unretained, we need to release it
    [locationManager setDelegate:nil];
}

- (void)mapView:(MKMapView *)mapView didUpdateUserLocation:(MKUserLocation *)userLocation
{
    CLLocationCoordinate2D loc = [userLocation coordinate];
    MKCoordinateRegion region = MKCoordinateRegionMakeWithDistance(loc, 250, 250);
    [worldView setRegion:region animated:YES];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [self findLocation];
    
    [textField resignFirstResponder];
    
    return YES;
}


@end
