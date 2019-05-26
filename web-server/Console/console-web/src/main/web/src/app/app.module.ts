import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PropertiesComponent } from './properties/properties.component';
import { BlocktreeComponent } from './blocktree/blocktree.component';
import { MenuComponent } from './menu/menu.component';
import { StatusComponent } from './status/status.component';
import { HardwareEditorComponent } from './hardware-editor/hardware-editor.component';
import { MessageService } from './services/message.service';
import { GraphService } from './services/graph.service';
import { PackageService } from './services/package.service';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from './material.module';

@NgModule({
  declarations: [
    AppComponent,
    PropertiesComponent,
    BlocktreeComponent,
    MenuComponent,
    StatusComponent,
    HardwareEditorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MaterialModule
  ],
  providers: [
     GraphService,
     MessageService,
     PackageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
