import { Component, OnInit } from '@angular/core';
import { NgForm } from "@angular/forms";
declare var $: any;
declare var require: any;

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent implements OnInit {

  constructor(
 
  ) {
  }

  ngOnInit() { }

  createProduct(productForm: NgForm) {
    // productForm.value["productId"] = "PROD_" + 1;
    // productForm.value["productAdded"] = "sa";
    // productForm.value["ratings"] = Math.floor(Math.random() * 5 + 1);
    // if (productForm.value["productImageUrl"] === undefined) {
    //   productForm.value["productImageUrl"] =
    //     "http://via.placeholder.com/640x360/007bff/ffffff";
    // }

    // productForm.value["favourite"] = false;

    // const date = productForm.value["productAdded"];

    // console.log(moment.unix(date).format("MM/DD/YYYY hh:mm:ss"));

   // this.productService.createProduct(productForm.value);

    $("#exampleModalLong").modal("hide");

  }

}
