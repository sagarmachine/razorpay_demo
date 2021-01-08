import logo from './logo.svg';
import './App.css';
import axios from 'axios';
import { Component, React } from 'react';




class App extends Component{

  state={
    amt:0,
    pmt:"",
    ramt:-1,
  }


   paymentHandler = () => {

    const API_URL = 'http://localhost:8080/'
    const orderUrl = `${API_URL}order/${this.state.amt}`;


  axios.get(orderUrl).then(res=>{
this.takePayment(res.data)
    })

  }

  takePayment=(response)=>{

    console.log(response);
    const API_URL = 'http://localhost:8080/'


    const options = {
      key:response.razorPay.secretKey ,
      name: "Your App Name",
      description: "Some Description",
      order_id: response.razorpayOrderId,
      amount:+response.razorPay.applicationFee,
      handler: async (response) => {
        try {
         const paymentId = response.razorpay_payment_id;
         const url = `${API_URL}capture/${paymentId}/${this.state.amt}`;


         const captureResponse =  axios.post(url, {})
          
         axios.get(url).then(res=>console.log(res.data));

       //  console.log(captureResponse.data);
        } catch (err) {
          console.log(err);
        }
      },
      theme: {
        color: "#686CFD",
      },
    };
    const rzp1 = new window.Razorpay(options);
    rzp1.open();
    };


    refundHandler=()=>{

      if(this.state.ramt===-1)
         axios.post("http://localhost:8080/refund",this.state.pmt)
      .then(res=>console.log(res.data));

      else
      axios.post("http://localhost:8080/refund/"+this.state.ramt,this.state.pmt)
      .then(res=>console.log(res.data));
    }


  render(){
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
      
      </header>


 <form>
        <script src="https://checkout.razorpay.com/v1/payment-button.js" data-payment_button_id="pl_GAbXsrL5h6ZYP6"> 
        </script> </form>

         <input value={this.state.amt} onChange={(e)=>this.setState({amt:e.target.value})} type="number"/>
      <button onClick={this.paymentHandler}>
        GO
      </button>
        
        <br/><br/><br/>
        <hr/>
        <br/><br/><br/>

        <input value={this.state.pmt} onChange={(e)=>this.setState({pmt:e.target.value})} type="text"/>
        <input value={this.state.ramt} onChange={(e)=>this.setState({ramt:e.target.value})} type="number"/>

        <button onClick={this.refundHandler}>
        GO
      </button>
        

     

    </div>
  );
  }


}

export default App;
