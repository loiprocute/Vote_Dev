import React  from "react";
import IconService from 'icon-sdk-js'
const src='https://cdn.jsdelivr.net/npm/icon-sdk-js@latest/build/icon-sdk-js.web.min.j'
const httpProvider = new IconService.HttpProvider('https://sejong.net.solidwallet.io/api/v3');
const iconService = new IconService(httpProvider);
const iconConverter = IconService.IconConverter;
const iconAmount = IconService.IconAmount;
const iconWallet = IconService.IconWallet;
const iconBuilder = IconService.IconBuilder;
const SignedTransaction = IconService.SignedTransaction;



class WalletBasic extends React.Component{
    callMethod = ({ from, to, method, params, value }, handleSuccess) => {
        const customEvent = new CustomEvent('ICONEX_RELAY_REQUEST', {
            detail: {
                type: 'REQUEST_JSON-RPC',
                payload: {
                    jsonrpc: "2.0",
                    method: "icx_sendTransaction",
                    id: 133,
                    params: iconConverter.toRawTransaction({
                        from,
                        to,
                        value: iconAmount.of(value || 0, iconAmount.Unit.ICX).toLoop(),
                        dataType: "call",
                        nid: "0x53",
                        timestamp: (new Date()).getTime() * 1000,
                        stepLimit: iconConverter.toBigNumber(1000000),
                        version: iconConverter.toBigNumber(3),
                        data: {
                            method,
                            params,
                        }
                    })
                }
            }
        });
        window.dispatchEvent(customEvent);
        console.log(customEvent)
        
        const eventHandler = event => {
            window.removeEventListener('ICONEX_RELAY_RESPONSE', eventHandler);
        
            const { type, payload } = event.detail;
            if (type === 'RESPONSE_JSON-RPC') {
                console.log(payload.result); 
                handleSuccess(payload);
            }
            else if (type === 'CANCEL_JSON-RPC') {
                console.error('User cancelled JSON-RPC request');
            }
        }
        window.addEventListener('ICONEX_RELAY_RESPONSE', eventHandler);
        }
    handleSubmit = (e) => {
            e.preventDefault();
            const customEvent = new CustomEvent('ICONEX_RELAY_REQUEST', {
                detail: {
                    type: 'REQUEST_JSON-RPC',
                    payload: {
                        jsonrpc: "2.0",
                        method: "icx_call",
                        id: 6339,
                        params: {
                            from: this.state.myAddress, // TX sender address
                            to: this.state.VoteContract,   // SCORE address
                            dataType: "call",
                            data: {
                                method: "addCandidate", // SCORE external function
                                params: {
                                        'name': this.state.nameCan,
                                        'party': this.state.party,
                                        'qual': this.state.qualification,
                                        "imgURL": this.state.imageurl
                                }
                            }
                        }
                    }
                }
            });
            window.dispatchEvent(customEvent);
            const eventHandler = event => {
                const { type, payload } = event.detail;
                console.log("payload",payload);
                if (type === 'RESPONSE_JSON-RPC') {
                    console.log(payload); // e.g., {"jsonrpc": "2.0", "id": 6339, "result": { ... }}
                    
                }
                else if (type === 'CANCEL_JSON-RPC') {
                    console.error('User cancelled JSON-RPC request')
                }
            }
            window.addEventListener('ICONEX_RELAY_RESPONSE', eventHandler);
        }
    getMethod({ from, to, method, params }, handleSuccess) {
            const customEvent = new CustomEvent('ICONEX_RELAY_REQUEST', {
                detail: {
                    type: 'REQUEST_JSON-RPC',
                    payload: {
                        jsonrpc: "2.0",
                        method: "icx_call",
                        id: 133,
                        params: {
                            from,
                            to,
                            dataType: "call",
                            data: {
                                method,
                                params,
                            }
                        }
                    }
                }
            });
            window.dispatchEvent(customEvent);
          
            console.log(customEvent)
        
        const eventHandler = event => {
            window.removeEventListener('ICONEX_RELAY_RESPONSE', eventHandler);
        
            const { type, payload } = event.detail;
            if (type === 'RESPONSE_JSON-RPC') {
                handleSuccess(payload);
            }
            else if (type === 'CANCEL_JSON-RPC') {
                console.error('User cancelled JSON-RPC request');
            }
        }
        window.addEventListener('ICONEX_RELAY_RESPONSE', eventHandler);
    }
    //key:value
    
    state ={
        name:'my-app',
        myAddress:'',
        VoteContract : 'cx87fdbd89bbe7fe670a55544006310f7f3bdc8a4a',//'cx451a3d5eaff2c676fc1a5087c48b831cc69d21e2',//'cxc28c0a76d8d08fa934b392898e8bd63c5d26f280',
        status:'',
        qualification: '',
        start : '',
        end : '',
        poolName: '',
        nameCan:'',
        qual : '',
        checkAddress: '',
        indexPool: '' ,
        imgURL : '',
        result: '',
        indexVote: '',

    }
    
    handleOnChangeName=(event) => {
        console.log(event.target.value,'event.target',event.target ,'event',event)
        //this.state.name =event.target.value  Bad code !!!
        this.setState(
            {
                name:event.target.value
            }
        )
    }

    handleOnClickButton_Connect = (e) => {
        e.preventDefault()
        const customEvent = new CustomEvent('ICONEX_RELAY_REQUEST', {
            detail: {
                type: 'REQUEST_ADDRESS'
            }
        });
        window.dispatchEvent(customEvent);

        const eventHandler = event => {
            const { type, payload } = event.detail;
            if (type === 'RESPONSE_ADDRESS') {
                console.log(payload); // e.g., hx19870922...
                this.setState(
                    {
                        myAddress:payload
                    }
                )
            }
        }
        window.addEventListener('ICONEX_RELAY_RESPONSE', eventHandler);
    }
    handleOnClickButton_Name = () => {
            const customEvent = new CustomEvent('ICONEX_RELAY_REQUEST', {
                detail: {
                    type: 'REQUEST_JSON-RPC',
                    payload: {
                        jsonrpc: "2.0",
                        method: "icx_call",
                        id: 6339,
                        params: {
                            from: this.state.myAddress, // TX sender address
                            to: this.state.VoteContract,   // SCORE address
                            dataType: "call",
                            data: {
                                method: "name", // SCORE external function
                                params: {}
                            }
                        }
                    }
                }
            });
            window.dispatchEvent(customEvent);

            const eventHandler = event => {
                const { type, payload } = event.detail;
                console.log("payload",payload);
                if (type === 'RESPONSE_JSON-RPC') {
                   // e.g., {"jsonrpc": "2.0", "id": 6339, "result": { ... }}
                    this.setState(
                        {
                            status:payload.result
                        }
                    )
                }
                else if (type === 'CANCEL_JSON-RPC') {
                    console.error('User cancelled JSON-RPC request')
                }
            }
            window.addEventListener('ICONEX_RELAY_RESPONSE', eventHandler);
        }
    
    handlePoolName(event) {
        this.setState({poolName: event.target.value});
    }
    handleStart(event) {
        this.setState({start: event.target.value});
    }
    handleEnd(event) {
        this.setState({end: event.target.value});
    }
    handleIndexPool(event) {
        this.setState({indexPool: event.target.value});
    }
    handleNameCan(event) {
        this.setState({nameCan: event.target.value});
    }
    handleQual(event) {
        this.setState({qual: event.target.value});
    }
    handleImg(event) {
        this.setState({imgURL: event.target.value});
    }
    handleCheckAddress(event) {
        this.setState({checkAddress: event.target.value});
    }
    handleIndexVote(event) {
        this.setState({indexVote : event.target.value});
    }

    handleRegistration = (e) =>{
        e.preventDefault();
        this.callMethod({
                from: this.state.myAddress,
                to:  this.state.VoteContract,
                method: "registration",
                params: {
                        address: this.state.myAddress,
                        }
            }, (payload) => {
                    console.log("success");
        });

    }
    handleCreatePool = (e) =>{
        e.preventDefault();
        this.callMethod({
                from: this.state.myAddress,
                to:  this.state.VoteContract,
                method: "createPool",
                params: {
                        poolName: this.state.poolName,
                        start: this.state.start,
                        end : this.state.end,
                        }
            }, (payload) => {
                    console.log("success");
        });

    }
    handleAddCandidate = (e)=>{
        e.preventDefault();
        this.callMethod({
                from: this.state.myAddress,
                to:  this.state.VoteContract,
                method: "addCandidate",
                params: {
                        indexPool: this.state.indexPool,
                        name: this.state.nameCan,
                        qual : this.state.qual,
                        imgURL: this.state.imgURL
                        }
            }, (payload) => {
                    console.log("success");
        });
    }

    handleCheckPool = (e)=>{
        e.preventDefault();
        this.getMethod({
                from: this.state.myAddress,
                to:  this.state.VoteContract,
                method: "toJsonFormat_Pool",
                params: {
                        address: this.state.checkAddress,
                        }
            }, (payload) => {
                this.setState(
                    {
                        result:payload.result
                    }
                )
                    console.log("success");
        });
    }

    handleCheckNumPool = (e)=>{
        e.preventDefault();
        this.getMethod({
                from: this.state.myAddress,
                to:  this.state.VoteContract,
                method: "getNumPoolByAddress",
                params: {
                        address: this.state.checkAddress,
                        }
            }, (payload) => {

                    console.log("success");
        });
    }
    handleVote  = (e)=>{
        e.preventDefault();
        this.callMethod({
                from: this.state.myAddress,
                to:  this.state.VoteContract,
                method: "Vote",
                params: {
                        manager_contract: this.state.checkAddress,
                        indexPool :this.state.indexPool,
                        indexVote : this.state.indexVote
                        }
            }, (payload) => {

                    console.log("success");
        });
    }

    render(){
        /*
        let name ='Huu Loi'
        */
      //console.log('>>> call render : ',this.state )
        return (
            <>
                
                {/* <script src="https://cdn.jsdelivr.net/npm/icon-sdk-js@latest/build/icon-sdk-js.web.min.js"></script> */}
                <h1>
                    Connect Wallet
                </h1>
                <div>
                    <button onClick={(e) => {this.handleOnClickButton_Connect(e)}}>Click me </button>
                </div>

                <div className="first">
                    My Address is : {this.state.myAddress}
                </div>
                {/* <h1>
                    2.Name Contract
                </h1>
                <div>
                    <button onClick={() => {this.handleOnClickButton_Name()}}>Click me </button>
                </div>
                <div className="second">
                    Contract name : {this.state.status}
                </div> */}
                <h1>
                   Registration
                </h1>
                <input
                    type="text"
                    id="name"
                    placeholder="My Address"
                    value={this.state.myAddress}
                    //onChange={(e) => this.HandleRegistration(e)}
                />
                <button
                    onClick={(e) =>this.callMethod({
                        from: this.state.myAddress,
                        to:  this.state.VoteContract,
                        method: "registration",
                        params: {
                                address: this.state.myAddress,
                                }
                    }, (payload) => {
                            console.log("success");
                })}
                >
                    Register
                </button>
                <h1>
                   4.Create Pool
                </h1>   
                <form onSubmit={(e) => this.handleCreatePool(e)}>
                    <label>
                        Pool name:
                    <input type="text" value={this.state.poolName} onChange={(e) => this.handlePoolName(e)} />
                    </label>
                    <label>
                        Start time:
                    <input type="text" value={this.state.start} onChange={(e) => this.handleStart(e)} />
                    </label>
                    <label>
                        End time:
                    <input type="text" value={this.state.end} onChange={(e) => this.handleEnd(e)} />
                    </label>
                    <input type="submit" value="Submit" />
                </form>
                


                <h1>
                    addCandidate
                </h1>   
                <form onSubmit={(e) => this.handleAddCandidate(e)}>
                    <label>
                        indexPool:
                    <input type="text" value={this.state.indexPool} onChange={(e) => this.handleIndexPool(e)} />
                    </label>
                    <label>
                        name candidate:
                    <input type="text" value={this.state.nameCan} onChange={(e) => this.handleNameCan(e)} />
                    </label>
                    <label>
                        qual:
                    <input type="text" value={this.state.qual} onChange={(e) => this.handleQual(e)} />
                    </label>
                    <label>
                        imgURL:
                    <input type="text" value={this.state.imgURL} onChange={(e) => this.handleImg(e)} />
                    </label>


                    <input type="submit" value="Submit" />
                </form>
                
                <h1>
                    Vote (chủ vote không thể vote)
                </h1>   
                <form onSubmit={(e) => this.handleVote(e)}>
                    <label>
                        indexPool:
                    <input type="text" value={this.state.indexPool} onChange={(e) => this.handleIndexPool(e)} />
                    </label>
                    <label>
                        indexVote:
                    <input type="text" value={this.state.indexVote} onChange={(e) => this.handleIndexVote(e)} />
                    </label>
                    <label>
                        manager_contract:
                    <input type="text" value={this.state.checkAddress} onChange={(e) => this.handleCheckAddress(e)} />
                    </label>

                    <input type="submit" value="Submit" />
                </form>
                  


                <h1>
                   6.Check Pool
                </h1>   
                <form onSubmit={(e) => this.handleCheckPool(e)}>
                    <label>
                        indexPool:
                    <input type="text" value={this.state.checkAddress} onChange={(e) => this.handleCheckAddress(e)} />
                    </label>
                    

                    <input type="submit" value="Submit" />
                </form>
                <div >
                   result : {this.state.result}
                </div>

            
               
            </>  



                
            
        )
    }

}
export default WalletBasic ;