import { Component, OnInit, inject, signal, computed } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { RatingModule } from 'primeng/rating';

const emptyProduct: Product = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [DataViewModule, CardModule, ButtonModule, DialogModule, ProductFormComponent, RatingModule],
})
export class ProductListComponent implements OnInit {
  private readonly productsService = inject(ProductsService);

  public readonly products = this.productsService.products;

  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);
  cart = signal<Map<number, number>>(new Map());

  cartCount = computed(() => {
    let total = 0;
    this.cart().forEach(qty => total += qty);
    return total;
  });

  addToCart(product: Product) {
    const current = this.cart();
    const updated = new Map(current);
    updated.set(product.id, (updated.get(product.id) ?? 0) + 1);
    this.cart.set(updated);
  }

  removeFromCart(product: Product) {
    const current = this.cart();
    const updated = new Map(current);
    const currentQty = updated.get(product.id) ?? 0;
    if (currentQty <= 1) {
      updated.delete(product.id);
    } else {
      updated.set(product.id, currentQty - 1);
    }
    this.cart.set(updated);
  }

  getProductQuantity(productId: number): number {
    return this.cart().get(productId) ?? 0;
  }

  ngOnInit() {
    this.productsService.get().subscribe();
  }

  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProduct.set(product);
  }

  public onDelete(product: Product) {
    this.productsService.delete(product.id).subscribe();
  }

  public onSave(product: Product) {
    if (this.isCreation) {
      this.productsService.create(product).subscribe();
    } else {
      this.productsService.update(product).subscribe();
    }
    this.closeDialog();
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }
}
