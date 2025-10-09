import {useState} from "react";
import type {Product} from "../model/Product.tsx";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import type {GroceryListItem} from "../model/GroceryListItem.ts";
import "./groceries.css"

type NewGroceryListProps = {
    products: Product[]
}

export default function CreateNewList(props: NewGroceryListProps) {
    const availableProducts: Product[] = props.products ?? [];

    const nav = useNavigate();
    const [groceryName, setGroceryName] = useState<string>("")
    const [quantity, setQuantity] = useState<number>(1)
    const [groceryListItems, setGroceryListItems] = useState<GroceryListItem[]>([])

    function save() {
        axios.post("api/grocery-list", {
            products: groceryListItems,
            status: "OPEN"
        })
            .then(() => nav("/allGroceryLists"))
            .catch(error => console.log(error));
    }

    function addProductToTheGroceryList() {
        const product: Product | undefined = availableProducts
            .find((product: Product) =>
                product.name.toLowerCase().includes(groceryName.trim().toLowerCase())
            );

        if (product) {
            const newItem: GroceryListItem = {
                product: product,
                quantity: quantity
            };

            setGroceryListItems(prevItems => [...prevItems, newItem]);
            setGroceryName("");
            setQuantity(1);
        } else {
            alert("This product must first be added to the product list before it can be added to the groceries list!");
        }
    }

    return (
        <div>
            <h2>New grocery list</h2>
            <form className={"form"}>
                <p>You can choose from the list or add a product in the input field.</p>

                <div className={"display-flex"}>
                    <label>Available products</label>

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
                        {availableProducts.map(p =>
                            <option> {p.name} </option>
                        )}
                    </select>
                </div>

                <div className={"display-flex"}>
                    <label> Selected product </label>
                    <input
                        type={"text"}
                        name={"groceryName"}
                        value={groceryName}
                        className={"form-field"}
                        onChange={event => setGroceryName(event.target.value)}
                        onKeyDown={(e) => {
                            if (e.key === 'Enter') {
                                e.preventDefault();
                                addProductToTheGroceryList();
                            }
                        }}
                    />
                    <input
                        type={"number"}
                        name={"quantity"}
                        value={quantity}
                        className={"form-field amount"}
                        onChange={(event) => setQuantity(+event.target.value)}
                    />

                    {
                        groceryName.trim().length > 0 && quantity > 0
                            ? <button type={"button"} onClick={addProductToTheGroceryList}> Add </button>
                            : ""
                    }
                </div>

                {
                    groceryListItems.length > 0 && <>
                        <div className={"product-list"}>
                            {groceryListItems.map(item =>
                                <p key={item.product.name}>
                                    {item.product.name} ({item.quantity} units)
                                </p>
                            )}
                        </div>

                        <button
                            type="button"
                            className={"save-button"}
                            onClick={save}
                        >
                            Save
                        </button>
                    </>
                }
            </form>
        </div>
    );
}