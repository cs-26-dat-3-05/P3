function EmailCorrespondance(props: {theEmails: emailsObject}){ 
    // props are just preliminary defined untill we establish connection between frontend and mailserver //
    // Also the types of objects are also suggesting //

    return(
    <>
    <fieldset>
        <div id="correspondanceTitle">   
            <p>Korrenspondance med arrangør</p> <p>{props.theEmails.AllEmailsArray.length} EMAILS</p>
            <br></br>
            <h3>EMAILTRÅD</h3>
            <hr></hr>
        </div>

        <div id="correspondance">
            {props.theEmails.AllEmailsArray.map((Email, index) => (
                <div key={`KeyEmail${index}`} className="Emails">
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
                    <hr></hr>
                </div>
            ))}
        </div>
    </fieldset>
    </>
    );
}

export default EmailCorrespondance;

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