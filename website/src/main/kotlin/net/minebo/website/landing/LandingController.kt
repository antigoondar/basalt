package ltd.matrixstudios.website.landing

import net.minebo.basalt.models.website.BasaltUser
import ltd.matrixstudios.website.ranks.RankRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest

/**
 * Class created on 11/24/2023

 * @author 98ping
 * @project Basalt
 * @website https://solo.to/redis
 */
@Controller
class LandingController @Autowired constructor(private val rankRepository: RankRepository) {

    @RequestMapping(value = ["/", "/home"], method = [RequestMethod.GET])
    fun onLandRequest(): ModelAndView = ModelAndView("login")

    @RequestMapping(value = ["/dashboard", "/panel"], method = [RequestMethod.GET])
    fun onDashboardRequest(request: HttpServletRequest) : ModelAndView
    {
        val modelAndView = ModelAndView("home")
        val profile = request.session.getAttribute("user") as BasaltUser? ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "You must be logged in to view this page")

        modelAndView.addObject("user", profile)
        modelAndView.addObject("rankSize", rankRepository.findAll().size)
        return modelAndView
    }
}