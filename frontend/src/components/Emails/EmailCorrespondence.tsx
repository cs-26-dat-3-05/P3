import styled from "styled-components";

const Correspondence = styled.div`
    border: var(--border);
    background-color: var(--background-color);
    color: var(--primary-color);
    padding: 24px;
    margin-top: 30px;
    width: 1150px;
    margin-right: auto;  
    max-width: 100%;
    box-sizing: border-box;
    min-height: 650px;
    box-shadow: var(--shadow-sm);


    #correspondanceTitle {
        display: flex;
        justify-content: space-between;
        align-items: center;
        flex-wrap: wrap;
        margin-top: 0;
    }

    .titleLeft p {
        margin-top: 0;
    }
        
    #correspondanceTitle hr{
        width: 100%;
    }

    #correspondance{
        margin-bottom: 0;
    }

    #possibleReplies{
        margin-top: 20px;
    }

    details p {
        padding: 12px;
        background-color: #f1eded;
        white-space: pre-wrap;
    }

    .Emails {
        display: flex;
        gap: 24px;
        border-bottom: 1px solid #bcbcbc;
    }

    .emailBadge {
        border: var(--border-sm);
        width: 44px;
        height: 44px;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 15px;
    }
  
`


function EmailCorrespondence(props: {theEmails: emailsObject}){ 
    // props are just preliminary defined untill we establish connection between frontend and mailserver //
    // Also the types of objects are also suggesting //

    return(
    <Correspondence>
        <div id="correspondanceTitle"> 
            <div className="titleLeft">
                <p>Korrenspondance med arrangør</p> 
                <h3>EMAILTRÅD</h3>
            </div>  
            <div className="titleRight">
                <p>{props.theEmails.AllEmailsArray.length} EMAILS</p>
            </div>
            <hr></hr>
        </div>

        <div id="correspondance">
            {props.theEmails.AllEmailsArray.map((Email, index) => (
                <div key={`KeyEmail${index}`} className="Emails">
                    
                    <div className="emailBadge">
                        UD
                    </div>

                    <div className="emailContext">
                        <p>{Email.mailStatus} - {Email.mailType}</p>
                        <p>{Email.date.toLocaleString("da-DK", {day: "numeric", month: "numeric", year:"numeric"})}, kl. {Email.date.toLocaleString("da-DK", {hour: "2-digit", minute: "2-digit"})} - Til {Email.receiver}</p>
                        <p>{Email.note}</p>
                        <br></br>
                        <details>
                            <summary>Vis Emailbesked</summary>
                                <p>{Email.emailBody}</p>
                        </details>
                        <br></br>
                        <br></br>
                    </div>
                </div>
            ))}
        </div>

        <div id="possibleReplies">
            <fieldset>
                <p>Ingen svar modtaget. Hvis arrangøren svarer, vises svaret her med afsender, tidspunkt og besked.</p>
            </fieldset>
        </div>
    </Correspondence>
    );
}

export default EmailCorrespondence;

export type email = {
    mailType: string;
    mailStatus: string;
    receiver: string
    sender: string;
    date: Date;
    note: string;
    emailBody: string;
}

export type emailsObject = {
    AllEmailsArray: email[];
}