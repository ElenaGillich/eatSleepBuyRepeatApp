import {type FormEvent, useEffect, useState} from "react";
import axios from "axios";

import {useNavigate, useParams} from "react-router-dom";
import type {GroceryList} from "./model/GroceryList.tsx";
import type {Status} from "./model/Status.ts"
import type {Product} from "./model/Product.tsx";
import type {GroceryListItem} from "./model/GroceryListItem.ts";

export default function EditGroceryListForm() {

    const params = useParams();
    const nav = useNavigate();
    const [title, setTitle] = useState<string>("");
    const [status, setStatus] = useState<Status>("OPEN");
    const [products, setProducts] = useState<Product[]>([]);

    const [quantity, setQuantity] = useState<number>(1)
    const [groceryListItems, setGroceryListItems] = useState<GroceryListItem[]>([])

    const [groceryName, setGroceryName] = useState<string>("")

    useEffect(() => {
        getAllProducts()
    }, []);

    function getAllProducts(){
        axios.get("api/products")
            .then(r => setProducts(r))
            .catch(e => console.log(e))
    }

    const [groceryList, setGroceryList] = useState<GroceryList>({
        id: "",
        title: "",
        products: [],
        status: "OPEN"
    });

    function updateGroceryList(e: FormEvent) {
        e.preventDefault();
        setGroceryList({
            id: groceryList.id,
            title: title,
            products: groceryList.products,
            status: status
        })
        console.log(groceryList)
        console.log("params: ", params)
        axios.put("/api/grocery-list/" + params, groceryList).then(() => nav("/"))
            .catch(e => console.log(e))
    }

    function addProductToTheGroceryList() {
        const nameOfProductToAdd: string = groceryName.trim().toLowerCase();
        const product: Product | undefined = products
            .find((product: Product) =>
                product.name.toLowerCase() === nameOfProductToAdd
            );

        if (product) {
            const productAlreadyInTheList = groceryListItems
                .find(item => item.product.name.toLowerCase() == nameOfProductToAdd);

            const newItem: GroceryListItem = {
                product: product,
                quantity: productAlreadyInTheList ? (productAlreadyInTheList.quantity + quantity) : quantity
            };


            if (productAlreadyInTheList) {
                const reducedList = groceryListItems
                    .filter(item => item.product !== productAlreadyInTheList.product);

                setGroceryListItems(reducedList);
            }

            setGroceryListItems(prevItems => [...prevItems, newItem]);
            setGroceryName("");
            setQuantity(1);
        } else {
            alert("This product must first be added to the product list before it can be added to the groceries list!");
        }
    }

    return (
        <>
            <form onSubmit={updateGroceryList}>
                <label> Titel:
                    <input onChange={e => setTitle(e.target.value)}/>
                </label>
                <label>
                    <select
                        value={status}
                        onChange={e => setStatus(e.target.value as Status)}>
                        <option value="OPEN">OPEN</option>
                        <option value="DONE">DONE</option>
                    </select>
                </label>
                <label> Available products{' '}
                    <select
                        className={"form-field select"}
                        value={""}
                        onChange={(event) => setGroceryName(event.target.value)}
                        onKeyDown={(e) => {
                            if (e.key === 'Enter') {
                                e.preventDefault();
                                addProductToTheGroceryList();
                            }
                        }}
                    >
                        <option>
                            Products to select...
                        </option>

                        {products.map(p =>
                            <option key={p.name}> {p.name} </option>
                        )}
                    </select>
                </label>
                <button type={"submit"}>Save</button>
            </form>
        </>
    )
}