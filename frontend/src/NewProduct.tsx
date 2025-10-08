import {useState} from "react";
import type {Product} from "./model/Product.tsx";
import axios from "axios";
import {useNavigate} from "react-router-dom";

export default function NewProduct(){

    const [productName, setProductName] = useState<string>("");
    const [product, setProduct] = useState<Product>({id: "", name: ""});

    const nav = useNavigate();

    function onAddNewProduct(){
        setProduct({id: "1", name: productName})
        axios.post("/api/products", product)
            .then(response=> console.log(response))
            .catch(e=>console.log(e));
        nav("/allProducts");
    }

    return(
        <>
            <form onSubmit={onAddNewProduct}>
                <label> Name:
                    <input onChange={e => setProductName(e.target.value)}/>
                </label>
                <button type={"submit"}>Submit</button>
            </form>
        </>
    )
}